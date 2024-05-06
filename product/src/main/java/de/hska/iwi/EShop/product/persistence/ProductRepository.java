package de.hska.iwi.EShop.product.persistence;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Integer> {
    List<Product> findByPriceGreaterThanEqualAndPriceLessThanEqualAndDetailsLike(Double minPrice, Double maxPrice, String details);
}