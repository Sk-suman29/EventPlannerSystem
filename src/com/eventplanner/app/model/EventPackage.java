
package com.eventplanner.app.model;

public class EventPackage {

    private int id;
    private String category;
    private String name;
    private double price;

    public EventPackage(int id, String category, String name, double price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + category + ") Rs." + price;
    }
}
