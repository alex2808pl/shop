package de.telran.shop.repository;

import de.telran.shop.entity.Cart;
import de.telran.shop.entity.CartItems;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CartItemsRepositoryTest {


    @Autowired
    private CartItemsRepository cartItemsRepository;
    private final Long CARTITEMS_ID = 1L;
    private final long CART_ID = 2L;
    private final long PRODUCT_ID_NEW=1;
    private final int QUANTITY_ID_NEW=1;
    CartItems cartItemsTested = new CartItems();

    @Autowired
    private CartRepository cartRepository;
    Cart cart = new Cart();
    @BeforeEach
    void setUp() {
        cartItemsTested.setCart(cartRepository.findById(CART_ID).orElse(null));
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @Order(1)
    void testInsert() {

        cartItemsRepository.save(cartItemsTested);

        CartItems cartItemsExpected = cartItemsRepository.findById(cartItemsTested.getCartItemId()).orElse(null);

        assertNotNull(cartItemsExpected);
        assertEquals(cartItemsExpected.getCartItemId(), cartItemsExpected.getCartItemId());
        assertEquals(cartItemsExpected.getCart(), cartItemsExpected.getCart());
        assertTrue(cartItemsExpected.getCartItemId()>0);
    }
    @Test
    @Order(2)
    void testGet(){
        CartItems cartItemsExpected = cartItemsRepository.findById(CARTITEMS_ID).orElse(null);
        assertNotNull(cartItemsExpected);
        assertEquals(cartItemsExpected.getCartItemId(),CARTITEMS_ID);
        assertTrue(cartItemsExpected.getCartItemId()>0);
    }
    @Test
    @Order(3)
    void testEdit(){
        cartItemsRepository.save(cartItemsTested);
        cartItemsTested = cartItemsRepository.findById(cartItemsTested.getCartItemId()).orElse(null);
        cartItemsTested.setCart(cartRepository.findById(CART_ID).orElse(null));
        cartItemsTested.setQuantity(QUANTITY_ID_NEW);
        cartItemsTested.setProductId(PRODUCT_ID_NEW);
        cartItemsRepository.save(cartItemsTested);

        CartItems cartItemsExpected = cartItemsRepository.findById(cartItemsTested.getCartItemId()).orElse(null);

        assertNotNull(cartItemsExpected);
        assertEquals(cartItemsExpected.getCartItemId(),cartItemsTested.getCartItemId());
        assertEquals(cartItemsExpected.getCart(),cartItemsTested.getCart());
        assertEquals(cartItemsExpected.getQuantity(),cartItemsTested.getQuantity());
        assertEquals(cartItemsExpected.getProductId(),cartItemsTested.getProductId());

        assertTrue(cartItemsExpected.getCartItemId()>0);
    }

    @Test
    @Order(4)
    void testDelete() {
        cartItemsRepository.save(cartItemsTested);
        CartItems cartItemsExpected = cartItemsRepository.findById(cartItemsTested.getCartItemId()).orElse(null);
        assertNotNull(cartItemsExpected);

        cartItemsRepository.deleteById(cartItemsExpected.getCartItemId());

        cartItemsExpected = cartItemsRepository.findById(cartItemsTested.getCartItemId()).orElse(null);
        assertNull(cartItemsExpected);
    }
}