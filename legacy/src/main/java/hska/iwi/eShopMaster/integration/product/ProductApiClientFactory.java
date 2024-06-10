package hska.iwi.eShopMaster.integration.product;

public class ProductApiClientFactory {

    private static ApiClient client = null;

    public static ApiClient getClient() {
        if (client == null) {
            client = new ApiClient();
            client.setBasePath("http://product-service:8080");
        }
        return client;
    }

}
