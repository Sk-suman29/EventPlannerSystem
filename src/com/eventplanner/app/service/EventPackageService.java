package com.eventplanner.app.service;

import com.eventplanner.app.model.EventPackage;
import com.eventplanner.app.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventPackageService {

    public void addPackage(EventPackage p) {
        String sql = "INSERT INTO event_packages(id, category, name, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getId());
            ps.setString(2, p.getCategory());
            ps.setString(3, p.getName());
            ps.setDouble(4, p.getPrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EventPackage> getPackages() {
        List<EventPackage> list = new ArrayList<>();
        String sql = "SELECT id, category, name, price FROM event_packages";

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String category = rs.getString("category");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                list.add(new EventPackage(id, category, name, price));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public EventPackage getPackageById(int id) {
        String sql = "SELECT id, category, name, price FROM event_packages WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new EventPackage(
                            rs.getInt("id"),
                            rs.getString("category"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updatePackage(EventPackage p) {
        String sql = "UPDATE event_packages SET category = ?, name = ?, price = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getCategory());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePackage(int id) {
        String sql = "DELETE FROM event_packages WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
