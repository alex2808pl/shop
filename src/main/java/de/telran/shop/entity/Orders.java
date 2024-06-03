package de.telran.shop.entity;
import de.telran.shop.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
//    @Column(name = "orderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

//    @Column(name = "userId")
//    private String userId;

//    @Column(name = "createdAt")
    private Timestamp  createdAt;

//    @Column(name = "deliveryAddress")
    private String deliveryAddress;

//    @Column(name = "contactPhone")
    private String contactPhone;

//    @Column(name = "deliveryMethod")
    private String  deliveryMethod;

    @Enumerated(EnumType.STRING)
//    @Column(name = "status")
    private Status status;

//    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId", nullable=false)
    private Users users;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<OrderItems> orderItems = new HashSet<>();


}