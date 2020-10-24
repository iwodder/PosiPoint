package com.wodder.gui.menu;

import com.wodder.model.menu.*;

import javax.swing.*;

public class MenuButton extends JButton {

    private MenuItem menuItem;

    public MenuButton() {
    }

    public void addItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.setText(createText(menuItem));
    }

    public String createText(MenuItem item) {
        return "<html><center>"+ item.getName() +"<br><br>"+item.getPrice()+"</center></html>";
    }

    public MenuItem getMenuItem() {
        return this.menuItem;
    }


}
