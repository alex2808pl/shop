package de.telran.shop.repository;

import de.telran.shop.entity.Cart;
import de.telran.shop.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
}
