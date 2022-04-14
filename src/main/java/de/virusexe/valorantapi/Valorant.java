package de.virusexe.valorantapi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.virusexe.valorantapi.authentication.ValorantAuthentication;
import de.virusexe.valorantapi.authentication.ValorantHeader;

import java.io.IOException;
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
        jsonObject.addProperty("client_id", "play-valorant-web-prod");
        jsonObject.addProperty("nonce", 1);
        jsonObject.addProperty("redirect_uri", "https://playvalorant.com/opt_in");
        jsonObject.addProperty("response_type", "token id_token");

        post(new URL("https://auth.riotgames.com/api/v1/authorization"), jsonObject.toString());

        JsonObject authObject = new JsonObject();
        authObject.addProperty("type", "auth");
        authObject.addProperty("username", username);
        authObject.addProperty("password", password);

        JsonObject authResponse = put(new URL("https://auth.riotgames.com/api/v1/authorization"), authObject.toString()).get().getAsJsonObject();
        JsonObject responseObject = authResponse.get("response").getAsJsonObject();
        JsonObject parametersObject = responseObject.get("parameters").getAsJsonObject();
        String uri = String.valueOf(parametersObject.get("uri"));
        String[] parts = uri.replace("https://playvalorant.com/opt_in#", "").split("&");
        String token = parts[0].replace("access_token=", "").replace("\"", "");

        ValorantHeader valorantHeader = new ValorantHeader("AUTHORIZATION", "Bearer " + token);

        JsonObject entitlementResponse = post(new URL("https://entitlements.auth.riotgames.com/api/token/v1"), "{}", valorantHeader).get().getAsJsonObject();
        String entitlementToken = entitlementResponse.get("entitlements_token").getAsString();

        JsonObject userResponse = post(new URL("https://auth.riotgames.com/userinfo"), "{}", valorantHeader).get().getAsJsonObject();
        String userId = userResponse.get("sub").getAsString();

        return new ValorantAuthentication(userId, token, entitlementToken);
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

    public CompletableFuture<JsonElement> put(URL url, String json, ValorantHeader... headers) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url.toString()))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json").PUT(HttpRequest.BodyPublishers.ofString(json));

        for (ValorantHeader valorantHeader : headers) {
            if (valorantHeader != null) {
                builder.setHeader(valorantHeader.getKey(), valorantHeader.getValue());
            }
        }

        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenApply(JsonParser::parseString);
    }
}
