package de.hska.iwi.EShop.product.api;

import de.hska.iwi.EShop.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductDTO> createNewProduct(CreateNewProductRequestDTO productRequest) {
        final var product = productService.createProduct(productRequest.getProductName(), productRequest.getPrice(), productRequest.getCategoryId(), productRequest.getDetails());
        return ResponseEntity.ok(ProductDTO.builder()
                                    .id(product.getId())
                                    .name(product.getName())
                                    .price(product.getPrice())
                                    .categoryId(product.getCategoryId())
                                    .details(product.getDetails())
                                    .build());
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