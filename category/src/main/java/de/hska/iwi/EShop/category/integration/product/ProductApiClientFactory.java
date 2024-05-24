package de.hska.iwi.EShop.category.integration.product;

import de.hska.iwi.EShop.integration.product.ApiClient;
import de.hska.iwi.EShop.integration.product.api.ProductApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductApiClientFactory {
    @Bean
    public ProductApi categoryApi() {
        return new ProductApi(apiClient());
    }

    @Bean
    public ApiClient apiClient() {
        ApiClient client = new ApiClient();
        client.setBasePath("http://product-service:8080");
        return client;
    }
}
