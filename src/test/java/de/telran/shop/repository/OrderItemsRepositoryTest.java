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
class OrderItemsRepositoryTest {

    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UsersRepository usersRepository;

    private OrderItems testOrderItems;
    private Set<OrderItems> testOrderItemsSet = new HashSet<>();
    private Orders testOrders;
    private Users testUsers;




    @BeforeEach
    void setUp() {

        testUsers = new Users();
        usersRepository.save(testUsers);

        testOrders = new Orders();
        testOrders.setUsers(testUsers);
        ordersRepository.save(testOrders);

        testOrderItems = new OrderItems();
        testOrderItems.setOrders(testOrders);

        testOrderItemsSet.add(testOrderItems);
        testOrders.setOrderItems(testOrderItemsSet);



    }

    @AfterEach
    void tearDown() {
        orderItemsRepository.delete(testOrderItems);
    }

    @Test
    void givenOrderItem_whenExists_thenCanBeFoundById() {

        long testId = 1L;
        OrderItems existingOrderItems = orderItemsRepository.findById(testId).orElse(null);

        assertNotNull(existingOrderItems);
        assertTrue(existingOrderItems.getOrderItemId()>0);
        assertEquals(testId,existingOrderItems.getOrderItemId());
    }

    @Test
    void givenOrderItem_whenSaved_thenCanBeFoundById() {

        OrderItems savedOrderItems = orderItemsRepository.save(testOrderItems);
        OrderItems foundOrderItems = orderItemsRepository.findById(savedOrderItems.getOrderItemId()).orElse(null);

        assertNotNull(foundOrderItems);
        assertTrue(foundOrderItems.getOrderItemId()>0);
        assertEquals(savedOrderItems.getOrderItemId(), foundOrderItems.getOrderItemId());
        assertEquals(savedOrderItems.getOrders().getOrderId(), foundOrderItems.getOrders().getOrderId());
        assertEquals(savedOrderItems.getOrders().getUsers().getUserId(), foundOrderItems.getOrders().getUsers().getUserId());
    }

    @Test
    void givenOrderItem_whenUpdated_thenCanBeFoundByIdWithUpdatedData() {
        OrderItems savedOrderItems = orderItemsRepository.save(testOrderItems);
        OrderItems foundOrderItems = orderItemsRepository.findById(savedOrderItems.getOrderItemId()).orElse(null);
        foundOrderItems.setQuantity(584);
        OrderItems updatedOrderItems = orderItemsRepository.save(foundOrderItems);
        foundOrderItems = orderItemsRepository.findById(updatedOrderItems.getOrderItemId()).orElse(null);

        assertNotNull(foundOrderItems);
        assertTrue(foundOrderItems.getOrderItemId()>0);
        assertEquals(updatedOrderItems.getOrderItemId(), foundOrderItems.getOrderItemId());
        assertEquals(savedOrderItems.getOrders().getOrderId(), foundOrderItems.getOrders().getOrderId());
        assertEquals(savedOrderItems.getOrders().getUsers().getUserId(), foundOrderItems.getOrders().getUsers().getUserId());
    }
    @Test
    void givenOrderItem_whenDeleted_thenCanNotBeFoundById() {
        OrderItems savedOrderItems = orderItemsRepository.save(testOrderItems);
        OrderItems existingOrderItems = orderItemsRepository.findById(savedOrderItems.getOrderItemId()).orElse(null);
        assertNotNull(existingOrderItems);

        orderItemsRepository.delete(existingOrderItems);

        OrderItems deletedOrderItems = orderItemsRepository.findById(existingOrderItems.getOrderItemId()).orElse(null);
        assertNull(deletedOrderItems);
    }
}