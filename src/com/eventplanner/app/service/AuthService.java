package com.eventplanner.app.service;

import com.eventplanner.app.exceptions.AuthenticationException;
import com.eventplanner.app.model.SystemUser;

import java.util.ArrayList;
import java.util.List;

public class AuthService {

    private final List<SystemUser> users = new ArrayList<>();

    public AuthService() {

        users.add(new SystemUser("admin", "admin123", "ADMIN"));
        users.add(new SystemUser("staff", "staff123", "STAFF"));
    }

    public SystemUser authenticate(String username, String password) throws AuthenticationException {
        for (SystemUser u : users) {
            if (u.getUsername().equalsIgnoreCase(username)
                    && u.getPassword().equals(password)) {
                return u;
            }
        }
        throw new AuthenticationException("Invalid username or password");
    }
}