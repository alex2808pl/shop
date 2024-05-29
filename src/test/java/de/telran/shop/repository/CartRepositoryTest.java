package de.telran.shop.repository;

import de.telran.shop.entity.Cart;
import de.telran.shop.entity.Favorites;
import de.telran.shop.entity.Users;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.HashSet;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
    private final Long CART_ID = 1L;
    private final long USER_ID = 2L;
    Cart cartTested = new Cart();
    @Autowired
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
         cartTested.setCartItems(null);
         cartTested.setUser(null);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @Order(1)
    void testInsert() {

         cartRepository.save(cartTested);
         Cart cartExpected = cartRepository.findById(cartTested.getCartId()).orElse(null);

         assertNotNull(cartExpected);
         assertEquals(cartTested.getCartId(), cartExpected.getCartId());
         assertEquals(cartTested.getUser(), cartExpected.getUser());
         assertTrue(cartExpected.getCartId()>0);

    }
    @Test
    @Order(2)
    void testGet(){
        Cart cartExpected = cartRepository.findById(CART_ID).orElse(null);
        assertNotNull(cartExpected);
        assertEquals(cartExpected.getCartId(),CART_ID);
        assertTrue(cartExpected.getCartId()>0);
    }
    @Test
    @Order(3)
    void testEdit(){
         cartRepository.save(cartTested);
         cartTested = cartRepository.findById(cartTested.getCartId()).orElse(null);
         cartTested.setUser(usersRepository.findById(USER_ID).orElse(null));
         cartRepository.save(cartTested);

         Cart cartExpected = cartRepository.findById(cartTested.getCartId()).orElse(null);

         assertNotNull(cartExpected);
         assertEquals(cartExpected.getCartId(),cartTested.getCartId());
         assertEquals(cartExpected.getUser(),usersRepository.findById(USER_ID).orElse(null));
         assertTrue(cartExpected.getCartId()>0);
    }

    @Test
    @Order(4)
    void testDelete() {
        cartRepository.save(cartTested);
        Cart cartExpected = cartRepository.findById(cartTested.getCartId()).orElse(null);
        assertNotNull(cartExpected);

         cartRepository.deleteById(cartExpected.getCartId());

         cartExpected = cartRepository.findById(cartTested.getCartId()).orElse(null);
         assertNull(cartExpected);
    }

}