package de.telran.shop.repository;

import de.telran.shop.entity.Favorites;
import de.telran.shop.entity.Users;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FavoritesRepositoryTest {

    @Autowired
    private FavoritesRepository favoritesRepositoryTest;

    @Autowired
    private UsersRepository usersRepositoryTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    //@DisplayName("Тест на получение данных")
    void testGet(){
        Favorites favoritesExpected = new Favorites(1L, 1, null);
        Optional<Favorites> favoritesActual = favoritesRepositoryTest.findById(1L);
        Assertions.assertTrue(favoritesActual.isPresent());
        Assertions.assertEquals(favoritesExpected.getFavoriteId(),favoritesActual.get().getFavoriteId());
        Assertions.assertEquals(favoritesExpected.getProductId(),favoritesActual.get().getProductId());
    }

    @Test
    void testInsert(){
//        Optional<Users> usersActualTest = usersRepositoryTest.findById(1L);
//        Assertions.assertTrue(usersActualTest.isPresent());

        Favorites favoritesExpected = new Favorites();
        favoritesExpected.setProductId(1);

        // Заглушка для пользователя
        Users usersTest = new Users();
        usersTest.setUserId(1);

        favoritesExpected.setUsers(usersTest);

        Favorites favoritesActual = favoritesRepositoryTest.save(favoritesExpected);

        Assertions.assertNotNull(favoritesActual);
        Assertions.assertTrue(favoritesExpected.getFavoriteId()>0);
        Assertions.assertEquals(favoritesExpected.getProductId(),favoritesActual.getProductId());

    }

    @Test
    void testEdit(){
        Optional<Favorites> favoritesDb = favoritesRepositoryTest.findById(1L);
        Assertions.assertTrue(favoritesDb.isPresent());

        Favorites favoritesExpected = favoritesDb.get();
        favoritesExpected.setProductId(2);

        Favorites favoritesActual = favoritesRepositoryTest.save(favoritesExpected);
        Assertions.assertNotNull(favoritesActual);
        Assertions.assertEquals(favoritesExpected.getProductId(),favoritesActual.getProductId());
    }

    @Test
    //@DisplayName("Тест на удаление")
    void testDelete() {
        Optional<Favorites> favoritesForDelete = favoritesRepositoryTest.findById(1L);
        Assertions.assertTrue(favoritesForDelete.isPresent());

        favoritesRepositoryTest.delete(favoritesForDelete.get());

        Optional<Favorites> favoritesActual = favoritesRepositoryTest.findById(1L);
        Assertions.assertFalse(favoritesActual.isPresent());

    }
}