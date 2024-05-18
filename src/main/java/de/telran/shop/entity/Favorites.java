package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OrdersItems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Favorites {
    @Id
    @Column(name = "FavoriteID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long favoriteId;

    @Column(name = "UserID")
    private long userId;

    @Column(name = "ProductID")
    private long  productId;

}