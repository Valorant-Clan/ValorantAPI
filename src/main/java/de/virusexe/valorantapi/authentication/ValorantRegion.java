package de.virusexe.valorantapi.authentication;

public enum ValorantRegion {

    EU("https://pd.Eu.a.pvp.net");

    private final String url;

    ValorantRegion(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
