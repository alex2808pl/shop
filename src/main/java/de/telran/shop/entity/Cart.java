package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @Column(name = "CartId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;


//    @Column(name = "UserId")
//    private long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItems> cartItems = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", referencedColumnName = "userId")
    private Users user;
}