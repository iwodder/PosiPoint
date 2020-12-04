package com.wodder.model.menu;

public class MenuItem {

    private final String name;
    private String dollars;
    private String cents;
    private boolean outOfStock;

    public MenuItem(String name, String price) {
        validateInput(name, price);
        this.name = name;
        setDollarsAndCents(price);
    }

    public String getPrice() {
        return this.dollars + "." + this.cents;
    }

    public String getName() {
        return this.name;
    }

    public void setOutOfStock(boolean b) {
        this.outOfStock = b;
    }

    public boolean isOutOfStock() {
        return outOfStock;
    }

    public boolean isInStock() {
        return !isOutOfStock();
    }

    private void validateInput(String name, String price) {
        validateName(name);
        validatePrice(price);
    }

    private void validateName(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Must supply a name for a new menu item");
        }
    }

    private void validatePrice(String price) {
        if (price == null || price.length() == 0) {
            throw new IllegalArgumentException("Must supply a price for a new menu item");
        }
        if (!price.contains(".")) {
            throw new IllegalArgumentException("Price must contain '.', e.g. xx.yy");
        }
        int decimal = price.indexOf('.');
        if (price.substring(++decimal).length() > 2) {
            throw new IllegalArgumentException("Price must only have two places after the decimal, e.g. xx.yy");
        }
    }

    private void setDollarsAndCents(String price) {
        int decimal = price.indexOf('.');
        this.dollars = price.substring(0, decimal);
        this.cents = price.substring(++decimal);
    }
}
