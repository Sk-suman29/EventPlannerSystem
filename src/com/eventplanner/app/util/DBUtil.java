package com.eventplanner.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private static final String URL = "jdbc:sqlite:event_planner.db";

    static {
        // This static block runs once when the class is loaded
        try {
            initDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static void initDatabase() throws SQLException {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {

            // ---- Clients table ----
            st.execute("""
            CREATE TABLE IF NOT EXISTS clients (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                phone TEXT NOT NULL
            )
        """);

            // ---- Event Packages table ----
            st.execute("""
            CREATE TABLE IF NOT EXISTS event_packages (
                id INTEGER PRIMARY KEY,
                category TEXT NOT NULL,
                name TEXT NOT NULL,
                price REAL NOT NULL
            )
        """);

            // ---- Bookings table ----
            st.execute("""
            CREATE TABLE IF NOT EXISTS bookings (
                booking_id TEXT PRIMARY KEY,
                client_id  INTEGER NOT NULL,
                package_id INTEGER NOT NULL,
                event_date TEXT NOT NULL,
                FOREIGN KEY (client_id)  REFERENCES clients(id),
                FOREIGN KEY (package_id) REFERENCES event_packages(id)
            )
        """);
        }
    }

}