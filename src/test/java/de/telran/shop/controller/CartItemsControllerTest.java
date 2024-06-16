package de.telran.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.CartItemsDto;

import de.telran.shop.exceptions.CartItemNotFoundException;
import de.telran.shop.exceptions.CartItemWrongValueException;

import de.telran.shop.service.CartItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartItemsController.class)
public class CartItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartItemsService cartItemsServiceMock;


    private CartItemsDto cartItemsDto1;
    private CartItemsDto cartItemsDto2;

    @BeforeEach
    void setUp() {
        cartItemsDto1 = CartItemsDto.builder()
                .cartItemId(1l)
                .productId(4l)
                .quantity(12)
                .cart(CartDto.builder()
                                .cartId(1l)
                                .build())
                .build();
        cartItemsDto2 = CartItemsDto.builder()
                .cartItemId(2l)
                .productId(3l)
                .quantity(2)
                .cart(CartDto.builder()
                        .cartId(2l)
                        .build())
                .build();
    }
    @Test
    void getCartItems() throws Exception {
        when(cartItemsServiceMock.getCartItems()).thenReturn(List.of(cartItemsDto1,cartItemsDto2));

        this.mockMvc.perform(get("/cartitems")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..cartItemId").exists());
    }

    @Test
    void getCartItemById() throws Exception {
        Long id = 1l;
        when(cartItemsServiceMock.getCartItemsById(id)).thenReturn(cartItemsDto1);
        this.mockMvc.perform(get("/cartitems/{id}",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemId").exists())
                .andExpect(jsonPath("$.cartItemId").value(1));

        when(cartItemsServiceMock.getCartItemsById(id)).thenThrow(new CartItemNotFoundException("the given cart item was not found"));
        this.mockMvc.perform(get("/cartitems/{id}",id))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.cartItemId").doesNotExist())
                .andExpect(jsonPath("$.message").value("the given cart item was not found"));
    }

    @Test
    void insertCartItems() throws Exception {

        cartItemsDto2.setCartItemId(0);
        when(cartItemsServiceMock.insertCartItems(any(CartItemsDto.class))).thenReturn(cartItemsDto1);
        this.mockMvc.perform(post("/cartitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemsDto2)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cartItemId").exists())
                .andExpect(jsonPath("$..cartItemId").value(1));

        cartItemsDto2.setCart(null);
        when(cartItemsServiceMock.insertCartItems(any(CartItemsDto.class))).thenThrow(new CartItemWrongValueException("Failed to create cart item due to the wrong parameters"));
        this.mockMvc.perform(post("/cartitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartItemsDto2)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to create cart item due to the wrong parameters"));
    }

    @Test
    void deleteCartItemById() throws Exception {
        Long id = 1l;
        this.mockMvc.perform(delete("/cartitems/{id}",id))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.cartItemId").doesNotExist());

        doThrow(new CartItemNotFoundException("the given cart item cannot be deleted as it was not found")).when(cartItemsServiceMock).deleteCartItemById(id);
        this.mockMvc.perform(delete("/cartitems/{id}",id))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void updateCartItems() throws Exception {
        cartItemsDto1.setQuantity(5);
        when(cartItemsServiceMock.updateCartItems(any(CartItemsDto.class))).thenReturn(cartItemsDto1);
        this.mockMvc.perform(put("/cartitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemsDto1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemId").exists())
                .andExpect(jsonPath("$.cartItemId").value(1))
                .andExpect(jsonPath("$.quantity").value(5));

        cartItemsDto1.setCart(null);
        when(cartItemsServiceMock.updateCartItems(any(CartItemsDto.class))).thenThrow(new CartItemWrongValueException("Failed to update cart item`s data"));
        this.mockMvc.perform(put("/cartitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartItemsDto1)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}

