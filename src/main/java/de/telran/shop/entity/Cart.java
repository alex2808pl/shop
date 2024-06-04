package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
//    @Column(name = "cartId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;


//    @Column(name = "userId")
//    private long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItems> cartItems = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "userId")

    private Users users;
}