package de.telran.shop.service;

import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.Cart;
import de.telran.shop.repository.CartItemsRepository;
import de.telran.shop.repository.CartRepository;
import de.telran.shop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UsersRepository usersRepository;
    private final CartItemsRepository cartItemsRepository;

    public List<CartDto> getCart() {
        List<Cart> cartList = cartRepository.findAll();
        return cartList.stream()
                .map(f -> CartDto.builder()
                        .cartId(f.getCartId())
                        .users(new UsersDto(f.getUsers().getUserId(),
                                           usersRepository.findById(f.getUsers().getUserId()).orElse(null).getName(),
                                           usersRepository.findById(f.getUsers().getUserId()).orElse(null).getEmail(),
                                           usersRepository.findById(f.getUsers().getUserId()).orElse(null).getPhoneNumber(),
                                           usersRepository.findById(f.getUsers().getUserId()).orElse(null).getPasswordHash(),
                                           usersRepository.findById(f.getUsers().getUserId()).orElse(null).getRole()))
                        .build())
                .collect(Collectors.toList());
    }

    public CartDto getCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart != null) {
            return new CartDto(cart.getCartId(),
                               new UsersDto(cart.getUsers().getUserId(),
                                            cart.getUsers().getName(),
                                            cart.getUsers().getEmail(),
                                            cart.getUsers().getPhoneNumber(),
                                            cart.getUsers().getPasswordHash(),
                                            cart.getUsers().getRole()));
        }
        return null;
    }

    public void deleteCartById(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart != null) {
            cartRepository.delete(cart);
        }
    }

    public CartDto insertCart(CartDto cartDto) {
        if (cartDto.getUsers() != null &&  usersRepository.findById(cartDto.getUsers().getUserId()).orElse(null) !=null) {
            Cart cart = cartRepository.save(new Cart(0,
                    null,
                    usersRepository.findById(cartDto.getUsers().
                                    getUserId()).
                            orElse(null)));
            return new CartDto(cart.getCartId(),
                               new UsersDto(cartDto.getUsers().getUserId(),
                                            cartDto.getUsers().getName(),
                                            cartDto.getUsers().getEmail(),
                                            cartDto.getUsers().getPhoneNumber(),
                                            cartDto.getUsers().getPasswordHash(),
                                            cartDto.getUsers().getRole()));
        } else {
            return null;
        }
    }

    public CartDto updateCart(CartDto cartDto) {
        if (cartDto.getCartId() > 0 && cartDto.getUsers() != null) {
            Cart cart = cartRepository.findById(cartDto.getCartId()).orElse(null);
            if (cart != null && cart.getUsers() != null) {
                cart = cartRepository.save(new Cart(cartDto.getCartId(),
                                                    cartItemsRepository.findAll()
                                                                  .stream()
                                                                  .filter(cartItem -> cartItem
                                                                  .getCart()
                                                                  .getCartId() == cartDto.getCartId())
                                                                  .collect(Collectors.toSet()),
                                                    usersRepository.findById(cartDto.getUsers().getUserId()).orElse(null)));
                return new CartDto(cart.getCartId(),
                        new UsersDto(cartDto.getUsers().getUserId(),
                                cartDto.getUsers().getName(),
                                cartDto.getUsers().getEmail(),
                                cartDto.getUsers().getPhoneNumber(),
                                cartDto.getUsers().getPasswordHash(),
                                cartDto.getUsers().getRole()));
            }
        }
        return null;
    }
}