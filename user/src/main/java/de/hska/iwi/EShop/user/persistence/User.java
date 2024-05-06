package de.hska.iwi.EShop.user.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstname;

    @Column( nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String password;

    @ManyToOne()
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Role role;

    public User(String username, String firstname, String lastname, String password, Role role) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
    }
}
