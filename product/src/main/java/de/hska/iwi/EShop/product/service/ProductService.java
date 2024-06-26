package de.hska.iwi.EShop.product.service;

import de.hska.iwi.EShop.product.persistence.Product;
import de.hska.iwi.EShop.product.persistence.ProductRepository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> getFilteredProducts(Double minPrice, Double maxPrice, String details) {
        Specification<Product> spec = Specification.where(Product.withPriceRange(minPrice, maxPrice))
                                                    .and(Product.withDetails(details));
        return productRepository.findAll(spec);
    }

    public Optional<Product> getProductById(final int id) {
        return productRepository.findById(id);
    }

    public Product createProduct(final String productName, final Double price, final int categoryId, final String details) {
        final var product = Product.builder()
                                .name(productName)                                    
                                .price(price)
                                .categoryId(categoryId)
                                .details(details)
                                .build();

        return productRepository.save(product);
    }

    public boolean existsWithCategoryId(final int categoryId) {
        return !productRepository.findByCategoryId(categoryId).isEmpty();
    }

    public List<Product> getAllProductsForCategory(final int categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public void deleteProductById(final int id) {
        productRepository.deleteById(id);
    }
}
