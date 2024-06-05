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
//    private CartDto cartDto;
}
