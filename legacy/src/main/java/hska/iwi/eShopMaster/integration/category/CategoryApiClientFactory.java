package hska.iwi.eShopMaster.integration.category;

public class CategoryApiClientFactory {

    private static ApiClient client = null;

    public static ApiClient getClient() {
        if (client == null) {
            client = new ApiClient();
            client.setBasePath("http://category-service:8080");
        }
        return client;
    }

}
