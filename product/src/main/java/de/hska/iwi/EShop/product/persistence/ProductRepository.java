package de.hska.iwi.EShop.category.persistence;

import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Category, Integer> {
    @Query(value = "SELECT * FROM product WHERE price >= ?1 AND price <= ?2 WHERE name LIKE '%?3%'")
    List<User> getFilteredProducts(Integer minPrice, Integer maxPrice, String searchText);
}
