package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Table(name = "cartItems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItems {
    @Id
//    @Column(name = "cartItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartItemId;

//    @Column(name = "cartId")
//    private long cartId;

//    @Column(name = "productId")
    private long  productId;

    @Column(name = "Quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cartId", nullable=false)
    private Cart cart;
}