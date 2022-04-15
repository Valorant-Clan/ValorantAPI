package de.example.bot;

import de.virusexe.valorantapi.api.ValorantAPI;
import de.virusexe.valorantapi.authentication.ValorantRegion;

public class Main {

    public static void main(String[] args) {
        ValorantAPI valorantAPI = new ValorantAPI();

        try {
            valorantAPI.login("test", "test");

            //System.out.println("Store: " + valorantAPI.getStore(  valorantAPI.login("test", "test"), ValorantRegion.EU));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
