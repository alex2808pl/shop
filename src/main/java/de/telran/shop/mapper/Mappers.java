package de.telran.shop.mapper;

import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.CartItemsDto;
import de.telran.shop.dto.FavoritesDto;
import de.telran.shop.entity.Cart;
import de.telran.shop.entity.CartItems;
import de.telran.shop.entity.Favorites;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {

    private final ModelMapper modelMapper;

        public CartDto convertToCartDto(Cart cart) {
        return modelMapper.map(cart,CartDto.class);
    }

    public Cart convertToCart(CartDto cartDto) {
        return modelMapper.map(cartDto, Cart.class);
    }

    public CartItemsDto convertToCartItemsDto(CartItems cartItems) {
        return modelMapper.map(cartItems,CartItemsDto.class);
    }

    public CartItems convertToCartItems(CartItemsDto cartItemsDto) {
        return modelMapper.map(cartItemsDto, CartItems.class);
    }

    public FavoritesDto convertToFavoritesDto(Favorites favorites) {
        return modelMapper.map(favorites,FavoritesDto.class);
    }

    public Favorites convertToFavorites(FavoritesDto favoritesDto) {
        return modelMapper.map(favoritesDto, Favorites.class);
    }





//    public RecipientDto convertToRecipientDto(Recipient recipient) {
//        // перенастраиваю автомат, чтобы он пропустил поле iban и не отдал его клиенту
//        modelMapper.typeMap(Recipient.class, RecipientDto.class)
//                .addMappings(mapper -> mapper.skip(RecipientDto::setIban));
//
//        RecipientDto recipientDto = modelMapper.map(recipient, RecipientDto.class); //автомат
//        return recipientDto;
//    }
//
//    public Recipient convertToRecipient(RecipientDto recipientDto) {
//        Recipient recipient = modelMapper.map(recipientDto, Recipient.class); //автомат
//        recipient.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // можем заменить собственнім значением
//        return recipient;
//    }
//
//    public PurchaseOrderDto convertToPurchaseOrderDto(PurchaseOrder purchaseOrder) {
//        PurchaseOrderDto recipientDto = modelMapper.map(purchaseOrder, PurchaseOrderDto.class); //автомат
//        //подключаем руками нужный нам конвертор для подчиненного объекта, вместо автоматического
//        recipientDto.setRecipient(convertToRecipientDto(purchaseOrder.getRecipient()));
//        return recipientDto;
//    }


}
