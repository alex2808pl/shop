package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CartItems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItems {
    @Id
    @Column(name = "CartItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartItemId;

//    @Column(name = "CartId")
//    private long cartId;

    @Column(name = "ProductId")
    private long  productId;

    @Column(name = "Quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CartId", nullable=false)
    private Cart cart;
}