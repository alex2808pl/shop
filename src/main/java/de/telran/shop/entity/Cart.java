package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cart")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @Column(name = "CartID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @Column(name = "UserID")
    private long orderId;

}