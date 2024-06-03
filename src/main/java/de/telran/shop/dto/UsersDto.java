package de.telran.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.telran.shop.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDto {

    private Long userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private Role role;



    //    @JsonProperty("cart")
//    CartDto cartDto;



//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private Set<Orders> orders = new HashSet<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private Set<Favorites> favorites = new HashSet<>();
//
//    @OneToOne(mappedBy = "user")
//    private Cart cart;
}
