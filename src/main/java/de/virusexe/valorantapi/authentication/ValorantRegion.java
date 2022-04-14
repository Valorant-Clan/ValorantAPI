package de.virusexe.valorantapi.authentication;

public enum ValorantRegion {

    NA("https://pd.Na.a.pvp.net"),
    EU("https://pd.Eu.a.pvp.net"),
    AP("https://pd.Ap.a.pvp.net");

    private final String url;

    ValorantRegion(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
