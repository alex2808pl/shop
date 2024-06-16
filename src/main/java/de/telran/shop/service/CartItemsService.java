package de.telran.shop.service;

import de.telran.shop.config.MapperUtil;
import de.telran.shop.dto.CartItemsDto;
import de.telran.shop.exceptions.CartItemNotFoundException;
import de.telran.shop.exceptions.CartItemWrongValueException;
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
public class CartItemsService {

    private final CartRepository cartRepository;
    private final UsersRepository usersRepository;
    private final CartItemsRepository cartItemsRepository;
    private final Mappers mappers;
    private final ModelMapper modelMapper;

    public List<CartItemsDto> getCartItems() {
        return MapperUtil.convertList(cartItemsRepository.findAll(), mappers::convertToCartItemsDto);
    }

    public CartItemsDto getCartItemsById(Long id) {
        if(!cartItemsRepository.findById(id).isPresent()) {
            throw new CartItemNotFoundException("the given cart item was not found");
        }
        return mappers.convertToCartItemsDto(cartItemsRepository.findById(id).orElse(null));
    }

    public void deleteCartItemById(Long id) {
        if(!cartItemsRepository.findById(id).isPresent()) {
            throw new CartItemNotFoundException("the given cart item cannot be deleted as it was not found");
        }
        cartItemsRepository.findById(id).ifPresent(cartItemsRepository::delete);
    }

    public CartItemsDto insertCartItems(CartItemsDto cartItemsDto) {
        if (cartItemsDto.getCart() !=null
                && cartRepository.findById(cartItemsDto.getCart().getCartId()).orElse(null) != null ) {
                        return mappers.convertToCartItemsDto(cartItemsRepository.save(mappers.convertToCartItems(cartItemsDto)));
    }
        else {
            throw new CartItemWrongValueException("Failed to create cart item due to the wrong parameters");
        }
    }

    public CartItemsDto updateCartItems(CartItemsDto cartItemsDto) {
        if (cartItemsDto.getCartItemId() > 0
                && cartItemsRepository.findById(cartItemsDto.getCartItemId()).orElse(null) != null
                && cartRepository.findById(cartItemsDto.getCart().getCartId()).orElse(null) != null) {
                        return mappers.convertToCartItemsDto(cartItemsRepository.save(mappers.convertToCartItems(cartItemsDto)));
        }
        throw new CartItemWrongValueException("Failed to update cart item`s data");
    }
}

