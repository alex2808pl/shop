package de.telran.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Favorites {
    @Id
//    @Column(name = "favoriteId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long favoriteId;

//    @Column(name = "userId")
//    private long userId;

//    @Column(name = "productId")
    private long  productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", nullable=false)
    private Users users;

}
