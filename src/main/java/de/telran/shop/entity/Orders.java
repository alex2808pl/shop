package de.telran.shop.entity;
import de.telran.shop.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "Orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders {
    @Id
    @Column(name = "OrderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(name = "UserID")
    private String userId;

    @Column(name = "CreatedAt")
    private Timestamp  createdAt;

    @Column(name = "DeliveryAddress")
    private String deliveryAddress;

    @Column(name = "ContactPhone")
    private String contactPhone;

    @Column(name = "DeliveryMethod")
    private String  deliveryMethod;

    @Column(name = "Status")
    private Status status;

    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

}