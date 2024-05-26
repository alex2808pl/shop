package de.telran.shop.repository;

import de.telran.shop.entity.Cart;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

//    @SQL("select * from cart where userid = :userId")
//    List<Cart> getCartsByUserUserId(long userId);
}
