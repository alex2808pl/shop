package de.telran.shop.service;

import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.CartItemsDto;
import de.telran.shop.entity.Cart;
import de.telran.shop.entity.CartItems;
import de.telran.shop.entity.Users;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.CartItemsRepository;
import de.telran.shop.repository.CartRepository;
import org.h2.engine.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemsServiceTest {
    @Mock
    private CartItemsRepository cartItemsRepositoryMock;
    @Mock
    private CartRepository cartRepositoryMock;
    @Mock
    private Mappers mappers;
    @InjectMocks
    private CartItemsService cartItemsServiceMock;

    private CartItems cartItem1;
    private CartItems cartItem2;
    private Cart cart1;
    private Cart cart2;
    private CartItemsDto cartItemsDto1;
    private CartItemsDto cartItemsDto2;

    @BeforeEach
    void setUp() {
        cart1 = new Cart(
                1l,
                new HashSet<>(),
                new Users()
        );
        cart2 = new Cart(
                2l,
                new HashSet<>(),
                new Users()
        );
        cartItem1 = new CartItems(
                1l,
                3l,
                12,
                cart1
        );
        cartItem2 = new CartItems(
                2l,
                4l,
                7,
                cart2
        );
        cartItemsDto1 = CartItemsDto.builder()
                .cartItemId(1l)
                .productId(3l)
                .quantity(12)
                .cart(CartDto.builder()
                        .cartId(1l)
                        .build())
                .build();

        cartItemsDto2 = CartItemsDto.builder()
                .cartItemId(2l)
                .productId(4l)
                .quantity(7)
                .cart(CartDto.builder()
                        .cartId(2l)
                        .build())
                .build();
    }

    @Test
    void getCartItems() {
        when(cartItemsRepositoryMock.findAll()).thenReturn(List.of(cartItem1,cartItem2));
        when(mappers.convertToCartItemsDto(cartItem1)).thenReturn(cartItemsDto1);
        when(mappers.convertToCartItemsDto(cartItem2)).thenReturn(cartItemsDto2);

        List<CartItemsDto> cartItems = cartItemsServiceMock.getCartItems();
        Assertions.assertTrue(cartItems.size() > 0);
        verify(cartItemsRepositoryMock, Mockito.times(1)).findAll();
        verify(mappers,Mockito.times(2)).convertToCartItemsDto(any(CartItems.class));
    }

    @Test
    void getCartItemsById() {
        Long id = 1l;
        when(cartItemsRepositoryMock.findById(id)).thenReturn(Optional.of(cartItem1));
        when(mappers.convertToCartItemsDto(cartItem1)).thenReturn(cartItemsDto1);

        CartItemsDto cartItemsById = cartItemsServiceMock.getCartItemsById(id);

        Assertions.assertNotNull(cartItemsById);
        Assertions.assertEquals(cartItemsDto1.getCartItemId(),cartItemsById.getCartItemId());
        Assertions.assertEquals(cartItem1.getCart().getCartId(),cartItemsById.getCart().getCartId());

        verify(cartItemsRepositoryMock,Mockito.times(1)).findById(id);
        verify(mappers,Mockito.times(1)).convertToCartItemsDto(any(CartItems.class));
    }

    @Test
    void deleteCartItemById() {
        Long id = 1l;
        when(cartItemsRepositoryMock.findById(id)).thenReturn(Optional.of(cartItem1));

        cartItemsServiceMock.deleteCartItemById(id);
        verify(cartItemsRepositoryMock,Mockito.times(1)).delete(cartItem1);
    }

    @Test
    void insertCartItems() {
        Long id = 2l;
        CartItems cartItem3 = new CartItems(
                3l,
                8l,
                1,
                cart2
        );
        CartItemsDto cartItemsDto3 = CartItemsDto.builder()
                .cartItemId(3l)
                .productId(8l)
                .quantity(1)
                .cart(CartDto.builder()
                        .cartId(2l)
                        .build())
                .build();
        when(cartRepositoryMock.findById(id)).thenReturn(Optional.of(cart2));
        when(cartItemsRepositoryMock.save(any(CartItems.class))).thenReturn(cartItem3);
        when(mappers.convertToCartItemsDto(any(CartItems.class))).thenReturn(cartItemsDto3);
        when(mappers.convertToCartItems(any(CartItemsDto.class))).thenReturn(cartItem3);

        CartItemsDto cartItemsDto = cartItemsServiceMock.insertCartItems(cartItemsDto3);

        Assertions.assertNotNull(cartItemsDto);
        Assertions.assertEquals(cartItemsDto3.getCartItemId(),cartItemsDto.getCartItemId());

        verify(mappers,Mockito.times(1)).convertToCartItemsDto(any(CartItems.class));
        verify(mappers,Mockito.times(1)).convertToCartItems(any(CartItemsDto.class));
        verify(cartItemsRepositoryMock,Mockito.times(1)).save(any(CartItems.class));
        verify(cartRepositoryMock,Mockito.times(1)).findById(id);
    }

    @Test
    void updateCartItems() {
        Long id = 2l;
        CartItems cartItem3 = new CartItems(
                2l,
                8l,
                1,
                cart2
        );
        CartItemsDto cartItemsDto3 = CartItemsDto.builder()
                .cartItemId(2l)
                .productId(8l)
                .quantity(1)
                .cart(CartDto.builder()
                        .cartId(2l)
                        .build())
                .build();

        when(cartItemsRepositoryMock.findById(cartItem3.getCartItemId())).thenReturn(Optional.of(cartItem2));
        when(cartRepositoryMock.findById(id)).thenReturn(Optional.of(cart2));
        when(cartItemsRepositoryMock.save(any(CartItems.class))).thenReturn(cartItem3);
        when(mappers.convertToCartItemsDto(any(CartItems.class))).thenReturn(cartItemsDto3);

        CartItemsDto cartItemsDto = cartItemsServiceMock.updateCartItems(cartItemsDto3);

        assertNotNull(cartItemsDto);
        assertNotNull(cartItemsDto.getCartItemId());

        verify(mappers,Mockito.times(1)).convertToCartItemsDto(any(CartItems.class));
        verify(cartRepositoryMock,Mockito.times(1)).findById(id);
        verify(cartItemsRepositoryMock,Mockito.times(1)).save(any(CartItems.class));
        verify(cartItemsRepositoryMock,Mockito.times(2)).findById(cartItem3.getCartItemId());
    }
}