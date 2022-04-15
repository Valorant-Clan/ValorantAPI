package de.virusexe.valorantapi.authentication;

public record ValorantAuthentication(String uniqueId, String token,
                                     String entertainmentToken) {

    public String getUniqueId() {
        return uniqueId;
    }

    public String getToken() {
        return token;
    }

    public String getEntertainmentToken() {
        return entertainmentToken;
    }
}
