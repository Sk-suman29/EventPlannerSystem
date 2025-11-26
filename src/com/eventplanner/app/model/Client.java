package com.eventplanner.app.model;

 public class Client extends User {

   public Client(int id, String name, String phone) {
        super(id, name, phone);
    }

    @Override
    public String getUserType() {
        return "Client";
    }
}