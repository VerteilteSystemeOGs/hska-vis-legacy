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
        final var product = productService.createProduct(productRequest.getProductName());
        return ResponseEntity.ok(ProductDTO.builder().id(product.getId()).name(product.getName()).build());
    }

    @Override
    public ResponseEntity<Void> deleteProductById(Integer id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts().stream() 
                .map(product -> ProductDTO.builder() // Gehört der DTOBuilder in den Controller?
                        .id(product.getId())
                        .name(product.getName())
                        .build())
                .toList());
    }

    @Override
    public ResponseEntity<List<ProductDTO>> filterProducts(Integer minPrice, Integer maxPrice, String searchText) {
        return ResponseEntity.ok(productService.getFilteredProducts(minPrice, maxPrice, searchText).stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .build())
                .toList());
    }

    @Override
    public ResponseEntity<ProductDTO> getProductDetails(Integer id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .details(product.getDetails())
                        .price(product.getPrice())
                        .build())
                .orElse(ResponseEntity.notFound().build());
    }
}
