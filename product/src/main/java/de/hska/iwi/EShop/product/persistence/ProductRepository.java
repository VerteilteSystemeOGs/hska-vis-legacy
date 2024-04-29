package de.hska.iwi.EShop.product.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Integer> {
    @Query(value = "SELECT * FROM product WHERE price >= ?1 AND price <= ?2 WHERE name LIKE '%?3%'")
    List<Product> getFilteredProducts(Integer minPrice, Integer maxPrice, String searchText);
}