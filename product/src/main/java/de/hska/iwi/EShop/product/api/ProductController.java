package de.hska.iwi.EShop.product.api;

import de.hska.iwi.EShop.integration.category.ApiException;
import de.hska.iwi.EShop.integration.category.api.CategoryApi;
import de.hska.iwi.EShop.product.persistence.Product;
import de.hska.iwi.EShop.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;
    private final CategoryApi categoryApi;

    public ProductController(ProductService productService, CategoryApi categoryApi) {
        this.productService = productService;
        this.categoryApi = categoryApi;
    }

    @Override
    public ResponseEntity<ProductDTO> createNewProduct(CreateNewProductRequestDTO productRequest) {
        try {
            categoryApi.getCategoryById(productRequest.getCategoryId());
            if (categoryApi.getApiClient().getStatusCode() == 200) {
                final var product = productService.createProduct(productRequest.getProductName(), productRequest.getPrice(), productRequest.getCategoryId(), productRequest.getDetails());
                return ResponseEntity.ok(ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .categoryId(product.getCategoryId())
                        .details(product.getDetails())
                        .build());
            }
        } catch (ApiException e) {
            // something went wrong.
            // höchstwahrscheinlich ein 404 status code, weil die angegebene category id nicht existiert.
        }

        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<ProductDTO> getProductById(Integer id) {
        final var optionalProduct = productService.getProductById(id);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final var product = optionalProduct.get();
        return ResponseEntity.ok(ProductDTO.builder()
                                    .id(product.getId())
                                    .name(product.getName())
                                    .price(product.getPrice())
                                    .categoryId(product.getCategoryId())
                                    .build());
    }

    @Override
    public ResponseEntity<Void> deleteProductById(Integer id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> existsWithCategoryId(Integer categoryId) {
        if (productService.existsWithCategoryId(categoryId)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteAllWithCategoryId(Integer categoryId) {
        List<Product> productsToDelete = productService.getAllProductsForCategory(categoryId);
        for (Product productToDelete: productsToDelete) {
            productService.deleteProductById(productToDelete.getId());
        }

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts().stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .categoryId(product.getCategoryId())
                        .build())
                .toList());
    }

    @Override
    public ResponseEntity<List<ProductDTO>> filterProducts(Double minPrice, Double maxPrice, String searchText) {
        return ResponseEntity.ok(productService.getFilteredProducts(minPrice, maxPrice, searchText).stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .categoryId(product.getCategoryId())
                        .build())
                .toList());
    }

    @Override
    public ResponseEntity<ProductDTO> getProductDetails(Integer id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .categoryId(product.getCategoryId())
                        .details(product.getDetails())
                        .build()))
                .orElse(ResponseEntity.notFound().build());
    }
}
