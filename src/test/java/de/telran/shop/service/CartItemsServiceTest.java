package de.telran.shop.service;

import de.telran.shop.dto.CartItemsDto;
import de.telran.shop.dto.FavoritesDto;
import de.telran.shop.entity.Cart;
import de.telran.shop.entity.CartItems;
import de.telran.shop.entity.Favorites;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.CartItemsRepository;
import de.telran.shop.repository.FavoritesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class CartItemsServiceTest {
    @Mock
    private CartItemsRepository cartItemsRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private CartItemsService cartItemsServiceTest;

    private CartItemsDto expectedCartItemsDto;
    private CartItems expectedCartItems;

    @BeforeEach
    void setUp() {
        expectedCartItemsDto = CartItemsDto.builder()
                .cartItemId(1L)
                .productId(1)
                .quantity(1)
                .cart(null)
                .build();
        expectedCartItems = new CartItems(
                1L,
                1,
                1,
                new Cart()
        );
    }

    @Test
    void getCartItemsTest() {
        when(cartItemsRepositoryMock.findAll()).thenReturn(List.of(expectedCartItems));
        when(mappersMock.convertToCartItemsDto(any(CartItems.class))).thenReturn(expectedCartItemsDto);
        assertEquals(List.of(expectedCartItemsDto),cartItemsServiceTest.getCartItems());
    }

    @Test
    void getCartItemsByIdTest() {
        when(cartItemsRepositoryMock.findById(anyLong())).thenReturn(Optional.of(expectedCartItems));
        when(mappersMock.convertToCartItemsDto(any(CartItems.class))).thenReturn(expectedCartItemsDto);
        assertEquals(cartItemsServiceTest.getCartItemsById(expectedCartItems.getCartItemId()),expectedCartItemsDto);
        verify(mappersMock, times(1)).convertToCartItemsDto(any(CartItems.class));
    }

    @Test
    void deleteCartItemByIdTest() {
        long id = 1L;
        cartItemsServiceTest.deleteCartItemById(id);
        verify(cartItemsRepositoryMock,times(1)).deleteById(id);
    }

    @Test
    void insertCartItemsTest() {
    }

    @Test
    void updateCartItemsTest() {
    }
}