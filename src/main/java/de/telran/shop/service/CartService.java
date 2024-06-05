package de.telran.shop.service;

import de.telran.shop.config.MapperUtil;
import de.telran.shop.dto.CartDto;
import de.telran.shop.entity.Cart;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.CartItemsRepository;
import de.telran.shop.repository.CartRepository;
import de.telran.shop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UsersRepository usersRepository;
    private final CartItemsRepository cartItemsRepository;
    private final Mappers mappers;
    private final ModelMapper modelMapper;

    public List<CartDto> getCart() {
        return MapperUtil.convertList(cartRepository.findAll(), mappers::convertToCartDto);
    }

    public CartDto getCartById(Long id) {
        return mappers.convertToCartDto(cartRepository.findById(id).orElse(null));
    }

    public void deleteCartById(Long id) {
        cartRepository.findById(id).ifPresent(cartRepository::delete);
    }

    public CartDto insertCart(CartDto cartDto) {
        if (cartDto.getUsers() != null
                &&  usersRepository.findById(cartDto.getUsers().getUserId()).orElse(null) != null) {
            return mappers.convertToCartDto(cartRepository.save(mappers.convertToCart(cartDto)));
        } else {
            return null;
        }
    }

    public CartDto updateCart(CartDto cartDto) {
        if (cartDto.getCartId() > 0
                && cartDto.getUsers() != null
                && cartRepository.findById(cartDto.getCartId()).orElse(null) != null
                && usersRepository.findById(cartDto.getUsers().getUserId()).orElse(null) != null) {
            return mappers.convertToCartDto(cartRepository.save(mappers.convertToCart(cartDto)));
        }
          else {
              return null; }
    }
}
