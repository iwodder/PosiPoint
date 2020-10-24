package com.wodder.model.order;

import com.wodder.model.menu.*;
import com.wodder.model.users.*;
import org.junit.jupiter.api.*;

import javax.swing.table.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderModelTest {

    private TestUserSubject subject;
    private TestObserver observer;
    private OrderModel orderModel;
    private User testUser = new RegularUser("Test", "User");
    private User testUser2 = new RegularUser("Test2", "User");

    @BeforeEach
    void setUp() {
        subject = new TestUserSubject();
        observer = new TestObserver();
        orderModel = new OrderModel(new InMemory());
        orderModel.registerOrderObs(observer);
        subject.registerUserObserver(orderModel);
        subject.createTestUser();
    }

    @Test
    @DisplayName("Creating a new order notifies observers only once, new order has item count of zero")
    void createNewOrder() {
        orderModel.createNewOrder(testUser);
        assertEquals(1, observer.orderCreatedCtn);
        assertEquals(0, orderModel.getOrderList().size());
    }

    @Test
    @DisplayName("Adding an item to order should create new order, order created notification occurs, and item added notification occurs")
    void addSingleItem() {
        orderModel.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        assertEquals(1, observer.itemAdded);
        assertEquals(1, observer.orderCreatedCtn);
    }

    @Test
    @DisplayName("Newly added menu item should be present in the order item list")
    void addingMultipleItemsIsReflectedInList() {
        orderModel.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        orderModel.addItemToOrder(new MenuItem("Bacon Burger", "10.99"));
        Vector<String> orderList = orderModel.getOrderList();
        assertEquals(2, orderList.size());
    }

    @Test
    @DisplayName("Getting order price without an order causes IllegalStateException")
    void getOrderPriceWithoutOrder() {
        assertThrows(IllegalStateException.class, () -> orderModel.getOrderTotal());
    }

    @Test
    @DisplayName("Getting the order list without an order causes IllegalStateException")
    void getOrderListWithoutOrder() {
        assertThrows(IllegalStateException.class, () -> orderModel.getOrderList());
    }

    @Test
    @DisplayName("Getting the order id without an order causes IllegalStateException")
    void getOrderIdWithoutOrder() {
        assertThrows(IllegalStateException.class, () -> orderModel.getCurrentOrderId());
    }

    @Test
    @DisplayName("Deleting an order invalidates the order, getting id then fails")
    void deleteCurrentOrder() {
        orderModel.createNewOrder(testUser);
        orderModel.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        int firstOrderId = orderModel.getCurrentOrderId();
        orderModel.deleteOrder(testUser, firstOrderId);
        assertThrows(IllegalStateException.class, () -> orderModel.getCurrentOrderId());
        orderModel.createNewOrder(testUser);
        assertNotEquals(firstOrderId, orderModel.getCurrentOrderId());
    }

    @Test
    @DisplayName("Deleting an order then creating new order results in new order id")
    void deleteCurrentOrderAndCreateNew() {
        orderModel.createNewOrder(testUser);
        orderModel.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        int firstOrderId = orderModel.getCurrentOrderId();
        orderModel.deleteOrder(testUser, firstOrderId);

        orderModel.createNewOrder(testUser);
        assertNotEquals(firstOrderId, orderModel.getCurrentOrderId());
    }

    @Test
    @DisplayName("Able to save a working order")
    void saveOrder() {
        orderModel.createNewOrder(testUser);
        assertTrue(orderModel.saveOrder());
    }

    @Test
    @DisplayName("Able to save a working order and then lookup the order")
    void saveOrderAndLookup() {
        orderModel.createNewOrder(testUser);
        int id = orderModel.getCurrentOrderId();
        orderModel.saveOrder();
        assertThrows(IllegalStateException.class, () -> orderModel.getCurrentOrderId());
        orderModel.lookupOrder(testUser, id);
        assertEquals(id, orderModel.getCurrentOrderId());
    }

    @Test
    @DisplayName("Looking up orders for a user without any orders returns an empty iterator")
    void getOrders() {
       TableModel orders = orderModel.lookupOrders(testUser);
       assertEquals(0, orders.getRowCount());
    }

    @Test
    @DisplayName("Proper column headers are included in the model")
    void ordersModelHasProperColumns() {
        TableModel orders = orderModel.lookupOrders(testUser);
        assertEquals(4, orders.getColumnCount());
        assertEquals("Order Id", orders.getColumnName(0));
        assertEquals("Owner", orders.getColumnName(1));
        assertEquals("Total", orders.getColumnName(2));
        assertEquals("Item Count", orders.getColumnName(3));
    }

    @Test
    @DisplayName("Returns orders for a given user")
    void usersOrders() {
        orderModel.createNewOrder(testUser);
        orderModel.saveOrder();
        orderModel.createNewOrder(testUser);
        orderModel.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        orderModel.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        orderModel.addItemToOrder(new MenuItem("Bacon burger", "9.99"));
        orderModel.saveOrder();
        TableModel model = orderModel.lookupOrders(testUser);
        assertEquals(2, model.getRowCount());
    }

    @Test
    @DisplayName("Orders are only be returned for the supplied user")
    void onlyUsersOrders() {
        orderModel.createNewOrder(testUser);
        orderModel.saveOrder();

        orderModel.createNewOrder(testUser);
        orderModel.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        orderModel.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        orderModel.addItemToOrder(new MenuItem("Bacon burger", "9.99"));
        orderModel.saveOrder();

        orderModel.createNewOrder(testUser2);
        orderModel.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        orderModel.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        orderModel.addItemToOrder(new MenuItem("Bacon burger", "9.99"));
        orderModel.saveOrder();

        TableModel model = orderModel.lookupOrders(testUser);
        assertEquals(2, model.getRowCount());

        TableModel model2 = orderModel.lookupOrders(testUser2);
        assertEquals(1, model2.getRowCount());
    }
}