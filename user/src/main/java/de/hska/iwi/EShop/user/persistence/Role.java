package de.hska.iwi.EShop.user.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(name = "role_type")
    private String roleType;

    @Column(name = "role_level")
    private int roleLevel;
}
