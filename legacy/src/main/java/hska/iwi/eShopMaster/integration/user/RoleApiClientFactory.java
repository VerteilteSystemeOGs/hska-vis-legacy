package hska.iwi.eShopMaster.integration.user;

public class RoleApiClientFactory {
    private static ApiClient client = null;

    public static ApiClient getClient() {
        if (client == null) {
            client = new ApiClient();
            client.setBasePath("http://user-service:8080");
        }
        return client;
    }
}
