package com.eventplanner.app.model;

public class SystemUser {

    private final String username;
    private final String password;
    private final String role;  // e.g. "ADMIN" or "STAFF"

    public SystemUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    // In a real app you would hash passwords,
    // but for assignment/demo this is OK.
    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}
