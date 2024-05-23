package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "OrdersItems")
@Data
public class OrderItems {
    @Id
    @Column(name = "OrderItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderItemId;

//    @Column(name = "OrderId")
//    private long orderId;

    @Column(name = "ProductId")
    private long  productId;

    @Column(name = "Quantity")
    private long quantity;

    @Column(name = "PriceAtPurchase")
    private BigDecimal priceAtPurchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orderId", nullable=false)
    private Orders orders;
}