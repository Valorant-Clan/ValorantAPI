package de.virusexe.valorantapi.authentication;

public record ValorantHeader(String key, String value) {

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
