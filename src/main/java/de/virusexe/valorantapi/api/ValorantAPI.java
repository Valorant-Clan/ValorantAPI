package de.virusexe.valorantapi.api;

import com.google.gson.JsonObject;
import de.virusexe.valorantapi.Valorant;
import de.virusexe.valorantapi.authentication.ValorantAuthentication;
import de.virusexe.valorantapi.authentication.ValorantHeader;
import de.virusexe.valorantapi.authentication.ValorantRegion;

import java.net.URL;

public class ValorantAPI {

    private static final Valorant valorant = new Valorant();

    /**
     * Authenticate yourself to access the api with your login and password
     *
     * @param username the username of the account.
     * @param password the password of the account.
     * @return an authentication object with uniqueId, token and entertainment token.
     * @throws Exception if the request gets invalidated, or you get rate limited.
     */

    public ValorantAuthentication login(String username, String password) throws Exception {
        return valorant.authentication(username, password);
    }

    /**
     * Fetch your store using your valorant authentication and your valorant region
     * Make sure the region is the same region as the account because it will give you incorrect data.
     *
     * @param valorantAuthentication the valorant authentication object you receive when executing ValorantAPI#login
     * @param valorantRegion         the region the account is located in for the correct information.
     * @return a JsonObject containing all the store data.
     * @throws Exception if the request gets invalidated, or you get rate limited.
     */
    public JsonObject getStore(ValorantAuthentication valorantAuthentication, ValorantRegion valorantRegion) throws Exception {
        ValorantHeader authHeader = new ValorantHeader("AUTHORIZATION", "Bearer " + valorantAuthentication.getToken());
        ValorantHeader enHeader = new ValorantHeader("X-Riot-Entitlements-JWT", valorantAuthentication.getEntertainmentToken());

        return valorant.get(new URL(valorantRegion.getUrl() + "/store/v2/storefront/" + valorantAuthentication.getUniqueId()), authHeader, enHeader).get().getAsJsonObject();
    }

    /**
     * Fetches the item ids, so you can convert the uniqueIds of items to names.
     * Region isn't important here, so it can be any.
     *
     * @param valorantAuthentication the valorant authentication object you receive when executing ValorantAPI#login
     * @param valorantRegion         the region the account is located in for the correct information.
     * @param version                the version you want the items for.
     * @return a JsonObject containing all the ids.
     */
    public static JsonObject getIds(ValorantAuthentication valorantAuthentication, ValorantRegion valorantRegion, String version) throws Exception {
        ValorantHeader authHeader = new ValorantHeader("AUTHORIZATION", "Bearer " + valorantAuthentication.getToken());
        ValorantHeader enHeader = new ValorantHeader("X-Riot-Entitlements-JWT", valorantAuthentication.getEntertainmentToken());
        ValorantHeader verHeader = new ValorantHeader("X-Riot-ClientVersion", version);

        return valorant.get(new URL(valorantRegion.getUrl().replace("pd", "shared") + "/content-service/v2/content"), authHeader, enHeader, verHeader).get().getAsJsonObject();
    }
}
