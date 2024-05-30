package de.telran.shop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoritesDto {
    private long favoriteId;
    private long  productId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user")
    private UsersDto users;

}