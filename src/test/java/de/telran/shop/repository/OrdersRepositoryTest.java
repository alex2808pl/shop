package de.telran.shop.repository;

import de.telran.shop.entity.OrderItems;
import de.telran.shop.entity.Orders;
import de.telran.shop.entity.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrdersRepositoryTest {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UsersRepository usersRepository;
    private Orders testOrders;
    private Users testUsers;
    private OrderItems testOrderItems;
    private Set<OrderItems> testOrderItemsSet = new HashSet<>();

    @BeforeEach
    void setUp() {

        testUsers = new Users();
        usersRepository.save(testUsers);
        testOrders = new Orders();
        testOrders.setUsers(testUsers);

        testOrderItems = new OrderItems();
        testOrderItems.setOrders(testOrders);
        testOrderItemsSet.add(testOrderItems);
        testOrders.setOrderItems(testOrderItemsSet);
    }

    @AfterEach
    void tearDown() {
        ordersRepository.delete(testOrders);
    }

    @Test
    void givenOrder_whenExists_thenCanBeFoundById() {

        long testId = 1L;
        Orders existingOrders = ordersRepository.findById(testId).orElse(null);

        assertNotNull(existingOrders);
        assertTrue(existingOrders.getOrderId()>0);
        assertEquals(testId,existingOrders.getOrderId());
    }

    @Test
    void givenOrder_whenSaved_thenCanBeFoundById() {

        Orders savedOrders = ordersRepository.save(testOrders);
        Orders foundOrders = ordersRepository.findById(savedOrders.getOrderId()).orElse(null);

        assertNotNull(foundOrders);
        assertTrue(foundOrders.getOrderId()>0);
        assertEquals(savedOrders.getOrderId(), foundOrders.getOrderId());
        assertEquals(savedOrders.getUsers().getUserId(), foundOrders.getUsers().getUserId());
        assertEquals(savedOrders.getOrderItems().hashCode(), foundOrders.getOrderItems().hashCode());
    }

    @Test
    void givenOrder_whenUpdated_thenCanBeFoundByIdWithUpdatedData() {
        Orders savedOrders = ordersRepository.save(testOrders);
        Orders foundOrders = ordersRepository.findById(savedOrders.getOrderId()).orElse(null);
        foundOrders.setContactPhone("+491563287456");
        Orders updatedOrders = ordersRepository.save(foundOrders);
        foundOrders = ordersRepository.findById(updatedOrders.getOrderId()).orElse(null);

        assertNotNull(foundOrders);
        assertTrue(foundOrders.getOrderId()>0);
        assertEquals(updatedOrders.getOrderId(), foundOrders.getOrderId());
        assertEquals(updatedOrders.getUsers().getUserId(), foundOrders.getUsers().getUserId());
        assertEquals(updatedOrders.getOrderItems().hashCode(), foundOrders.getOrderItems().hashCode());
    }

    @Test
    void givenOrder_whenDeleted_thenCanNotBeFoundById() {
        Orders savedOrders = ordersRepository.save(testOrders);
        Orders existingOrders = ordersRepository.findById(savedOrders.getOrderId()).orElse(null);
        assertNotNull(existingOrders);

        ordersRepository.delete(existingOrders);

        Orders deletedOrders = ordersRepository.findById(existingOrders.getOrderId()).orElse(null);
        assertNull(deletedOrders);
    }

}