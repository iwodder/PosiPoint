package com.wodder.model.menu;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    private MenuItem menuItem;

    @BeforeEach
    void setup() {
        menuItem = new MenuItem("Cheeseburger", "10.99");
    }

    @Test
    @DisplayName("Can create a menu item")
    void createMenuItem() {
        MenuItem mi = new MenuItem("Cheeseburger", "11.00");
        assertNotNull(mi);
    }

    @Test
    @DisplayName("Cannot create menu item with an empty name")
    void emptyString() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new MenuItem("", "11.00"));
        assertNotNull(e);
        assertEquals("Must supply a name for a new menu item", e.getMessage());
    }

    @Test
    @DisplayName("Cannot create menu item with a null name")
    void nullString() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new MenuItem(null, "11.00"));
        assertNotNull(e);
        assertEquals("Must supply a name for a new menu item", e.getMessage());
    }

    @Test
    @DisplayName("Cannot create menu item with an empty price")
    void emptyPrice() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new MenuItem("Cheeseburger", ""));
        assertNotNull(e);
        assertEquals("Must supply a price for a new menu item", e.getMessage());
    }

    @Test
    @DisplayName("Cannot create menu item with a null price")
    void nullPrice() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new MenuItem("Cheeseburger", ""));
        assertNotNull(e);
        assertEquals("Must supply a price for a new menu item", e.getMessage());
    }

    @Test
    @DisplayName("Price must contain a decimal point")
    void noDecimalPoint() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new MenuItem("Cheeseburger", "10"));
        assertNotNull(e);
        assertEquals("Price must contain '.', e.g. xx.yy", e.getMessage());
    }

    @Test
    @DisplayName("Price has two integers after decimal")
    void limitTwoPlaces() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new MenuItem("Cheeseburger", "10.123"));
        assertNotNull(e);
        assertEquals("Price must only have two places after the decimal, e.g. xx.yy", e.getMessage());
    }

    @Test
    @DisplayName("Price returned matches price provided")
    void matchingPrice() {
        assertEquals("10.99", menuItem.getPrice());
    }
}