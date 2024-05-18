package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "OrdersItems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItems {
    @Id
    @Column(name = "OrderItemID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderItemId;

    @Column(name = "OrderID")
    private long orderId;

    @Column(name = "ProductID")
    private long  productId;

    @Column(name = "Quantity")
    private long quantity;

    @Column(name = "PriceAtPurchase")
    private BigDecimal priceAtPurchase;

}