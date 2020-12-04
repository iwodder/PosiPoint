package com.wodder.gui.menu;

import com.wodder.model.menu.*;

import javax.swing.*;

public class MenuButton extends JButton {

    private MenuItem menuItem;
    static final ImageIcon icon = new ImageIcon(MenuButton.class.getResource("/images/menu/x.png"));
    private JLabel iconContainer;

    public MenuButton() {
    }

    public void addItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        setText(createText(menuItem));
        iconContainer = new JLabel(icon);
        iconContainer.setVisible(false);
        add(iconContainer);
    }

    public String createText(MenuItem item) {
        return "<html><center>"+ item.getName() +"<br><br>"+item.getPrice()+"</center></html>";
    }

    public MenuItem getMenuItem() {
        return this.menuItem;
    }

    public void setInStock() {
        menuItem.setOutOfStock(false);
        iconContainer.setVisible(false);
        super.setEnabled(true);
        revalidate();
    }

    public void setOutOfStock() {
        menuItem.setOutOfStock(true);
        iconContainer.setVisible(true);
        super.setEnabled(false);
        revalidate();
    }

    public boolean inStock() {
        if (menuItem != null) {
            return menuItem.isInStock();
        } else {
            return false;
        }
    }
}
