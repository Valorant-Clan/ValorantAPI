package de.virusexe.valorantapi.authentication;

public class ValorantAuthentication {

    private final String uniqueId;
    private final String token;
    private final String entertainmentToken;

    public ValorantAuthentication(String uniqueId, String token, String entertainmentToken) {
        this.uniqueId = uniqueId;
        this.token = token;
        this.entertainmentToken = entertainmentToken;
    }

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
