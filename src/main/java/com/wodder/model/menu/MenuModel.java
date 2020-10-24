package com.wodder.model.menu;

import com.wodder.gui.observers.*;

import java.util.*;
import java.util.function.*;

public class MenuModel implements MenuSubject {

    private final ArrayList<MenuItem> menuItems;
    private List<MenuObserver> observerList;


    public MenuModel() {
        menuItems = new ArrayList<>();
        observerList = new ArrayList<>();
        menuItems.add(new MenuItem("Cheeseburger", "10.99"));
        menuItems.add(new MenuItem("Bacon Cheeseburger", "12.99"));
        menuItems.add(new MenuItem("Plain Hamburger", "9.99"));
        menuItems.add(new MenuItem("Veggie Burger", "11.99"));
        menuItems.add(new MenuItem("Mushroom Swiss Burger", "12.99"));
    }



    public void addMenuItem(MenuItem mi) {
        menuItems.add(mi);
        updateObservers((m) -> m.menuItemAdded(mi));
    }

    public Iterator<MenuItem> getMenuItems() {
        return Collections.unmodifiableList(menuItems).iterator();
    }

    private void updateObservers(Consumer<MenuObserver> action) {
        observerList.forEach(action);
    }

    @Override
    public void registerMenuObserver(MenuObserver observer) {
        int idx = observerList.indexOf(observer);
        if (idx == -1) {
            observerList.add(observer);
        }
    }
}
