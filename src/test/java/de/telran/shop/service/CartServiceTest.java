package de.telran.shop.service;

import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.CartItemsDto;
import de.telran.shop.entity.Cart;
import de.telran.shop.entity.CartItems;
import de.telran.shop.entity.Favorites;
import de.telran.shop.entity.Users;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.CartItemsRepository;
import de.telran.shop.repository.CartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private CartService cartServiceTest;

    private CartDto expectedCartDto;
    private Cart expectedCart;
    @BeforeEach
    void setUp() {
        expectedCartDto = CartDto.builder()
                .cartId(1L)
                .users(null)
                .build();
        expectedCart = new Cart( 1L,
                null,
                new Users()
        );
    }

    @Test
    void getCartTest() {
        when(cartRepositoryMock.findAll()).thenReturn(List.of(expectedCart));
        when(mappersMock.convertToCartDto(any(Cart.class))).thenReturn(expectedCartDto);
        assertEquals(List.of(expectedCartDto),cartServiceTest.getCart());
    }

    @Test
    void getCartByIdTest() {
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(expectedCart));
        when(mappersMock.convertToCartDto(any(Cart.class))).thenReturn(expectedCartDto);
        assertEquals(cartServiceTest.getCartById(expectedCart.getCartId()),expectedCartDto);
        verify(mappersMock, times(1)).convertToCartDto(any(Cart.class));
    }

    @Test
    void deleteCartById() {
        long id = 1L;
        when(cartRepositoryMock.findById(id)).thenReturn(Optional.of(expectedCart));
        cartServiceTest.deleteCartById(id);
        verify(cartRepositoryMock,times(1)).delete(expectedCart);
    }

    @Test
    void insertCart() {
    }

    @Test
    void updateCart() {
    }
}