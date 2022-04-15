package de.virusexe.valorantapi;

import com.google.gson.*;
import de.virusexe.valorantapi.authentication.ValorantAuthentication;
import de.virusexe.valorantapi.authentication.ValorantHeader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Valorant {


    public ValorantAuthentication authentication(String username, String password) throws IOException, ExecutionException, InterruptedException {
        JsonObject jsonObject = new JsonObject();

        Gson gson = new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .create();

        jsonObject.addProperty("client_id", "play-valorant-web-prod");
        jsonObject.addProperty("nonce", 1);
        jsonObject.addProperty("redirect_uri", "https://playvalorant.com/opt_in");
        jsonObject.addProperty("response_type", "token id_token");

        post(new URL("https://auth.riotgames.com/api/v1/authorization"), gson.toJsonTree(jsonObject.toString()).getAsString());

        JsonObject authObject = new JsonObject();
        authObject.addProperty("type", "auth");
        authObject.addProperty("username", username);
        authObject.addProperty("password", password);

        System.out.println(put(new URL("https://auth.riotgames.com/api/v1/authorization"), authObject).join());

        /*
        JsonObject authResponse = put(new URL("https://auth.riotgames.com/api/v1/authorization"), authObject).get().getAsJsonObject();
        JsonObject responseObject = authResponse.get("response").getAsJsonObject();
        JsonObject parametersObject = responseObject.get("parameters").getAsJsonObject();
        String uri = String.valueOf(parametersObject.get("uri"));
        String[] parts = uri.replace("https://playvalorant.com/opt_in#", "").split("&");
        String token = parts[0].replace("access_token=", "").replace("\"", "");

        System.out.println(token);


         */
        return new ValorantAuthentication("userId", "token", "entitlementToken");
    }

    public CompletableFuture<JsonElement> post(URL url, String json, ValorantHeader... headers) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url.toString()))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json));

        for (ValorantHeader valorantHeader : headers) {
            if (valorantHeader != null) {
                builder.setHeader(valorantHeader.getKey(), valorantHeader.getValue());
            }
        }

        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenApply(JsonParser::parseString);
    }

    public CompletableFuture<JsonElement> get(URL url, ValorantHeader... headers) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url.toString()))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json").GET();

        for (ValorantHeader valorantHeader : headers) {
            if (valorantHeader != null) {
                builder.setHeader(valorantHeader.getKey(), valorantHeader.getValue());
            }
        }

        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenApply(JsonParser::parseString);
    }

    public CompletableFuture<JsonElement> put(URL url, JsonElement json, ValorantHeader... headers) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url.toString()))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json.toString()));

        for (ValorantHeader valorantHeader : headers) {
            if (valorantHeader != null) {
                builder.setHeader(valorantHeader.getKey(), valorantHeader.getValue());
            }
        }

        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenApply(JsonParser::parseString);
    }
}
