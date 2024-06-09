package de.hska.iwi.EShop.product.persistence;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    @PositiveOrZero
    private int categoryId;

    @Column(nullable = false)
    private String details;

    public static Specification<Product> withPriceRange(Double minPrice, Double maxPrice) {
        return (root, query, cb) -> {
            if (minPrice != null && maxPrice != null) {
                return cb.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else if (maxPrice != null) {
                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
            } else {
                return null;
            }
        };
    }

    public static Specification<Product> withDetails(String details) {
        System.out.println("details param: " + details);
        return (root, query, cb) -> details != null ? cb.like(root.get("details"), "%" + details + "%") : null;
        //return (root, query, cb) -> details != null ? cb.isTrue(cb.function("CONTAINS", Boolean.class, root.get("details"), cb.literal(details))) : null;
    }
}
