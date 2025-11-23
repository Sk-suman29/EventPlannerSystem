package com.eventplanner.app.service;

import com.eventplanner.app.model.Booking;
import com.eventplanner.app.model.Client;
import com.eventplanner.app.model.EventPackage;
import com.eventplanner.app.util.AppContext;
import com.eventplanner.app.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    public void createBooking(Booking b) {
        String sql = "INSERT INTO bookings(booking_id, client_id, package_id, event_date) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getBookingId());
            ps.setInt(2, b.getClient().getId());
            ps.setInt(3, b.getEventPackage().getId());
            ps.setString(4, b.getEventDate());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> getBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT booking_id, client_id, package_id, event_date FROM bookings";

        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String bookingId = rs.getString("booking_id");
                int clientId = rs.getInt("client_id");
                int packageId = rs.getInt("package_id");
                String date = rs.getString("event_date");

                // Get related client & package from services (they use DB too)
                Client client = AppContext.getClientService().getClientById(clientId);
                EventPackage pkg = AppContext.getPackageService().getPackageById(packageId);

                if (client != null && pkg != null) {
                    list.add(new Booking(bookingId, client, pkg, date));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }