package de.virusexe.valorantapi;

public class unused {

    /*
     /*
        JsonObject parametersObject = responseObject.get("parameters").getAsJsonObject();
        String uri = String.valueOf(parametersObject.get("uri"));
        String[] parts = uri.replace("https://playvalorant.com/opt_in#", "").split("&");
        String token = parts[0].replace("access_token=", "").replace("\"", "");
        ValorantHeader valorantHeader = new ValorantHeader("AUTHORIZATION", "Bearer " + token);
         */

    //System.out.println(valorantHeader);

        /*

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

     */
}
