package de.telran.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.CartItemsDto;
import de.telran.shop.service.CartItemsService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(CartItemsController.class)
class CartItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartItemsService cartItemsServiceMock;

    private CartItemsDto cartItemsExpected1, cartItemsExpected2;


    @BeforeEach
    void setUp() {
        cartItemsExpected1 = CartItemsDto.builder()
                .cartItemId(1L)
                .cart(null)
                .quantity(2)
                .productId(2)
                .build();
        cartItemsExpected2 = CartItemsDto.builder()
                .cartItemId(2L)
                .cart(null)
                .quantity(2)
                .productId(2)
                .build();


    }

    @Test
    void getCartItems() throws Exception {
        when(cartItemsServiceMock.getCartItems()).thenReturn(List.of(cartItemsExpected1,cartItemsExpected2));
        this.mockMvc.perform(get("/cartitems")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..cartItemId").exists());
    }

    @Test
    void getCartItemsById() throws Exception {
            when(cartItemsServiceMock.getCartItemsById(anyLong())).thenReturn(cartItemsExpected1);
            this.mockMvc.perform(get("/cartitems/{id}",1)).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.cartItemId").exists())
                    .andExpect(jsonPath("$.cartItemId").value(1));
    }

    @Test
    void deleteCartItemsById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/cartitems/{id}", id)).andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.cartItemId").doesNotExist());
    }

    @Test
    void insertCartItems() throws Exception {
        cartItemsExpected2.setCartItemId(0L);
        when(cartItemsServiceMock.insertCartItems(any(CartItemsDto.class))).thenReturn(cartItemsExpected1);
        this.mockMvc.perform(post("/cartitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemsExpected2)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cartItemId").exists())
                .andExpect(jsonPath("$.cartItemId").value(1));
    }

    @Test
    void updateCartItems() throws Exception  {
        cartItemsExpected2.setCartItemId(1L);
        when(cartItemsServiceMock.updateCartItems(any(CartItemsDto.class))).thenReturn(cartItemsExpected1);
        this.mockMvc.perform(put("/cartitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemsExpected2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemId").exists())
                .andExpect(jsonPath("$.cartItemId").value(1));
    }
}