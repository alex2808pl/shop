package de.telran.shop.controller;

import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.CartItemsDto;
import de.telran.shop.service.CartItemsService;
import de.telran.shop.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartServiceMock;

    private CartDto cartExpected1, cartExpected2;

    @BeforeEach
    void setUp() {
        cartExpected1 = CartDto.builder()
                .cartId(1L)
                .users(null)
                .build();
        cartExpected2 = CartDto.builder()
                .cartId(2L)
                .users(null)
                .build();
    }

    @Test
    void getCart() throws Exception {
        when(cartServiceMock.getCart()).thenReturn(List.of(cartExpected1,cartExpected2));
        this.mockMvc.perform(get("/cart")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..cartId").exists());
    }

    @Test
    void getCartById() throws Exception {
        when(cartServiceMock.getCartById(anyLong())).thenReturn(cartExpected1);
        this.mockMvc.perform(get("/cart/{id}",1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").exists())
                .andExpect(jsonPath("$.cartId").value(1));
    }

    @Test
    void deleteCartById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/cart/{id}", id)).andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.cartId").doesNotExist());
    }

    @Test
    void insertCart()  throws Exception {
        cartExpected2.setCartId(0L);
        when(cartServiceMock.insertCart(any(CartDto.class))).thenReturn(cartExpected1);
        this.mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartExpected2)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cartId").exists())
                .andExpect(jsonPath("$.cartId").value(1));
    }

    @Test
    void updateCart() throws Exception {
        cartExpected2.setCartId(1L);
        when(cartServiceMock.updateCart(any(CartDto.class))).thenReturn(cartExpected1);
        this.mockMvc.perform(put("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartExpected2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartId").exists())
                .andExpect(jsonPath("$.cartId").value(1));
    }
}