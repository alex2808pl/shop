package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Favorites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorites {
    @Id
    @Column(name = "FavoriteId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long favoriteId;

//    @Column(name = "UserId")
//    private long userId;

    @Column(name = "ProductId")
    private long  productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="UserId", nullable=false)
    private Users users;

}