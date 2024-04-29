package de.hska.iwi.EShop.product.persistence;

import jakarta.persistence.*;
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

    @ManyToOne()
    @Column(nullable = false)
    private int categoryId;

    @Column(nullable = true)
    private String details;
}
