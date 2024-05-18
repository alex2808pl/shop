package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CartItems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItems {
    @Id
    @Column(name = "CartItemID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartItemId;

    @Column(name = "CartID")
    private long cartId;

    @Column(name = "ProductID")
    private long  productId;

    @Column(name = "Quantity")
    private long quantity;

}