package de.telran.shop.entity;

import de.telran.shop.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
//    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

//    @Column(name = "name")
    private String name;

//    @Column(name = "email")
    private String email;

//    @Column(name = "phoneNumber")
    private String phoneNumber;

//    @Column(name = "passwordHash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
//    @Column(name = "role")
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "users", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Favorites> favorites = new HashSet<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Orders> orders = new HashSet<>();

}
