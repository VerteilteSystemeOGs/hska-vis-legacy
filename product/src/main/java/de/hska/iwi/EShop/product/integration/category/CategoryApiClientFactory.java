package de.hska.iwi.EShop.product.integration.category;

import de.hska.iwi.EShop.integration.category.ApiClient;
import de.hska.iwi.EShop.integration.category.api.CategoryApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryApiClientFactory {
    @Bean
    public CategoryApi categoryApi() {
        return new CategoryApi(apiClient());
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient();
    }
}
