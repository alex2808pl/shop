package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
//@Table(name = "OrderItems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    @Id
//    @Column(name = "orderItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderItemId;

//    @Column(name = "orderId")
//    private long orderId;

//    @Column(name = "productId")
    private long  productId;

//    @Column(name = "quantity")
    private long quantity;

//    @Column(name = "priceAtPurchase")
    private BigDecimal priceAtPurchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orderId", nullable=false)
    private Orders orders;


}