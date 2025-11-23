package com.eventplanner.app.service;

import com.eventplanner.app.model.Client;
import com.eventplanner.app.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    public void addClient(Client client) {
        String sql = "INSERT INTO clients(id, name, phone) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, client.getId());
            ps.setString(2, client.getName());
            ps.setString(3, client.getPhone());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // In real app, you might throw a custom exception
        }
    }

    public List<Client> getClients() {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT id, name, phone FROM clients";

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                list.add(new Client(id, name, phone));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Client getClientById(int id) {
        String sql = "SELECT id, name, phone FROM clients WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateClient(Client client) {
        String sql = "UPDATE clients SET name = ?, phone = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getName());
            ps.setString(2, client.getPhone());
            ps.setInt(3, client.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(int id) {
        String sql = "DELETE FROM clients WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
