package de.telran.shop.service;

import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.CartItemsDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.Cart;
import de.telran.shop.entity.CartItems;
import de.telran.shop.entity.Users;
import de.telran.shop.repository.CartItemsRepository;
import de.telran.shop.repository.CartRepository;
import de.telran.shop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemsService {

    private final CartRepository cartRepository;
    private final UsersRepository usersRepository;
    private final CartItemsRepository cartItemsRepository;

    public List<CartItemsDto> getCartItems() {
        List<CartItems> cartItemsList = cartItemsRepository.findAll();
        return cartItemsList.stream()
                .map(f -> CartItemsDto.builder()
                        .cartItemId(f.getCartItemId())
                        .cart( new CartDto(f.getCart().getCartId(),
                                           new UsersDto(f.getCart().getCartId(),
                                                        usersRepository.findById(f.getCart().getUser().getUserId()).orElse(null).getName(),
                                                        usersRepository.findById(f.getCart().getUser().getUserId()).orElse(null).getEmail(),
                                                        usersRepository.findById(f.getCart().getUser().getUserId()).orElse(null).getPhoneNumber(),
                                                        usersRepository.findById(f.getCart().getUser().getUserId()).orElse(null).getPasswordHash(),
                                                        usersRepository.findById(f.getCart().getUser().getUserId()).orElse(null).getRole())
                        ))
                        .build())
                .collect(Collectors.toList());
    }

    public CartItemsDto getCartItemsById(Long id) {
         CartItems cartItems = cartItemsRepository.findById(id).orElse(null);
         if(cartItems != null) {
                Users users = usersRepository.findById(cartRepository.findById(id).orElse(null).getUser().getUserId()).orElse(null);
                return new CartItemsDto(id,
                                    cartItems.getProductId(),
                                    cartItems.getQuantity(),
                                    new CartDto(cartItems.getCartItemId(),
                                                new UsersDto(cartRepository.findById(id).orElse(null).getUser().getUserId(),
                                                             users.getName(),
                                                             users.getEmail(),
                                                             users.getPhoneNumber(),
                                                             users.getPasswordHash(),
                                                             users.getRole())));
         }
         return null;
    }

    public void deleteCartItemById(Long id) {
        CartItems cartItems = cartItemsRepository.findById(id).orElse(null);
        if(cartItems != null) {
            cartItemsRepository.delete(cartItems);
        }
    }

    public CartItemsDto insertCartItems(CartItemsDto cartItemsDto) {
        Cart cart = cartRepository.findById(cartItemsDto.getCart().getCartId()).orElse(null);
        if(cartItemsDto.getCart() !=null && cart != null) {
        CartItems cartItems = cartItemsRepository.save( new CartItems(0,
                                            cartItemsDto.getProductId(),
                                            cartItemsDto.getQuantity(),
                                            cart));
        return new CartItemsDto(cartItems.getCartItemId(),
                                cartItemsDto.getProductId(),
                                cartItemsDto.getQuantity(),
                                new CartDto(cartItems.getCart().getCartId(),
                                            new UsersDto(cartItems.getCart().getUser().getUserId(),
                                                        cartItems.getCart().getUser().getName(),
                                                        cartItems.getCart().getUser().getEmail(),
                                                        cartItems.getCart().getUser().getPhoneNumber(),
                                                        cartItems.getCart().getUser().getPasswordHash(),
                                                        cartItems.getCart().getUser().getRole())));
    }
        else {
            return null;}
    }

    public CartItemsDto updateCartItems(CartItemsDto cartItemsDto) {
        if (cartItemsDto.getCartItemId() > 0) {
            CartItems cartItems = cartItemsRepository.findById(cartItemsDto.getCartItemId()).orElse(null);
            if (cartItems != null) {
                cartItems = cartItemsRepository.save(new CartItems(cartItemsDto.getCartItemId(),
                                                                   cartItemsDto.getProductId(),
                                                                   cartItemsDto.getQuantity(),
                                                                   cartRepository.findById(cartItemsDto.getCart().getCartId()).orElse(null)));
                return new CartItemsDto(cartItems.getCartItemId(),
                                        cartItemsDto.getProductId(),
                                        cartItemsDto.getQuantity(),
                                        new CartDto(cartItems.getCart().getCartId(),
                                                    new UsersDto(cartItems.getCart().getUser().getUserId(),
                                                    cartItems.getCart().getUser().getName(),
                                                    cartItems.getCart().getUser().getEmail(),
                                                    cartItems.getCart().getUser().getPhoneNumber(),
                                                    cartItems.getCart().getUser().getPasswordHash(),
                                                    cartItems.getCart().getUser().getRole())));
            }
        }
        return null;
    }
}

