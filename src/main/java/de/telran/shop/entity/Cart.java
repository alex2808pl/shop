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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users users;
}