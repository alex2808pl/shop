package de.telran.shop.repository;

import de.telran.shop.entity.Cart;
import de.telran.shop.entity.Favorites;
import de.telran.shop.entity.Orders;
import de.telran.shop.entity.Users;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;
    private Users testUsers;
    private Cart testCart = new Cart();

    private Favorites testFavorites = new Favorites();
    private Set<Favorites> testFavoritesSet = new HashSet<>();

    private Orders testOrders = new Orders();
    private Set<Orders> testOrdersSet = new HashSet<>();



    @BeforeEach
    public void setUp() {

        testUsers = new Users();
        testUsers.setCart(testCart);

        testFavorites.setUsers(testUsers);
        testFavoritesSet.add(testFavorites);
        testUsers.setFavorites(testFavoritesSet);

        testOrders.setUsers(testUsers);
        testOrdersSet.add(testOrders);
        testUsers.setOrders(testOrdersSet);
    }

    @AfterEach
    public void tearDown() {
        usersRepository.delete(testUsers);
    }

    @Test
    void givenUser_whenExists_thenCanBeFoundById() {

        long testId = 1L;
        Users existingUsers = usersRepository.findById(testId).orElse(null);

        assertNotNull(existingUsers);
        assertTrue(existingUsers.getUserId()>0);
        assertEquals(testId,existingUsers.getUserId());
    }


    @Test
    void givenUser_whenSaved_thenCanBeFoundById() {

        Users savedUsers = usersRepository.save(testUsers);
        Users foundUsers = usersRepository.findById(savedUsers.getUserId()).orElse(null);

        assertNotNull(foundUsers);
        assertTrue(foundUsers.getUserId()>0);
        assertEquals(savedUsers.getUserId(), foundUsers.getUserId());
        assertEquals(savedUsers.getCart().getCartId(), foundUsers.getCart().getCartId());
        assertEquals(savedUsers.getFavorites().hashCode(), foundUsers.getFavorites().hashCode());
        assertEquals(savedUsers.getOrders().hashCode(), foundUsers.getOrders().hashCode());
    }


    @Test
    void givenUser_whenUpdated_thenCanBeFoundByIdWithUpdatedData() {
        Users savedUsers = usersRepository.save(testUsers);
        Users foundUsers = usersRepository.findById(savedUsers.getUserId()).orElse(null);
        foundUsers.setPasswordHash("pass1");
        Users updatedUsers = usersRepository.save(foundUsers);
        foundUsers = usersRepository.findById(updatedUsers.getUserId()).orElse(null);

        assertNotNull(foundUsers);
        assertTrue(foundUsers.getUserId()>0);
        assertEquals(updatedUsers.getUserId(), foundUsers.getUserId());
        assertEquals(updatedUsers.getCart().getCartId(), foundUsers.getCart().getCartId());
        assertEquals(updatedUsers.getFavorites().hashCode(), foundUsers.getFavorites().hashCode());
        assertEquals(updatedUsers.getOrders().hashCode(), foundUsers.getOrders().hashCode());
    }

    @Test
    void givenUser_whenDeleted_thenCanNotBeFoundById() {
        Users savedUsers = usersRepository.save(testUsers);
        Users existingUsers = usersRepository.findById(savedUsers.getUserId()).orElse(null);
        assertNotNull(existingUsers);

        usersRepository.delete(existingUsers);

        Users deletedUsers = usersRepository.findById(existingUsers.getUserId()).orElse(null);
        assertNull(deletedUsers);
    }
}