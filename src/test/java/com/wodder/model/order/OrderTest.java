package com.wodder.model.order;

import com.wodder.model.menu.*;
import com.wodder.model.users.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private final User user = new RegularUser("Test", "User");

    @BeforeEach
    void setup() {

    }

    @Test
    @DisplayName("Orders should not have the same id")
    void orderCreate() {
        Order o1 = new Order(user);
        Order o2 = new Order(user);
        assertNotEquals(o1, o2);
    }

    @Test
    @DisplayName("Adding an item to order results in non-empty order and quantity of 1")
    void addMenuItem() {
        Order o = new Order(user);
        o.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        assertEquals(1, o.getTotalItems());
    }

    @Test
    @DisplayName("Order with no item returns zero price")
    void getPrice() {
        Order o = new Order(user);
        assertEquals("0.00", o.orderTotal());
    }

    @Test
    @DisplayName("Order with one item returns the item's price")
    void getTotalSingleItem() {
        Order o = new Order(user);
        o.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        assertEquals("10.99", o.orderTotal());
    }

    @Test
    @DisplayName("Order with two items returns the correct total price")
    void getTotalTwoItem() {
        Order o = new Order(user);
        o.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        o.addItemToOrder(new MenuItem("Bacon burger", "12.99"));
        assertEquals("23.98", o.orderTotal());
    }

    @Test
    @DisplayName("Order with duplicate item returns the correct total price")
    void getTotalDuplicateItem() {
        Order o = new Order(user);
        o.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        o.addItemToOrder(new MenuItem("Cheeseburger", "10.99"));
        assertEquals("21.98", o.orderTotal());
    }

    @Test
    @DisplayName("Attempting to create an Order without an owner causes IllegalArgumentException")
    void cannotMakeOrder() {
        assertThrows(IllegalArgumentException.class, () -> new Order(null));
    }
}