package de.hska.iwi.EShop.product.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    //List<Product> findByPriceGreaterThanEqualAndPriceLessThanEqualAndDetailsContains(Double minPrice, Double maxPrice, String details);
    List<Product> findByCategoryId(int categoryId);
}
