package com.eventplanner.app.ui;

import com.eventplanner.app.model.SystemUser;
import com.eventplanner.app.service.BookingService;
import com.eventplanner.app.service.ClientService;
import com.eventplanner.app.service.EventPackageService;
import com.eventplanner.app.util.AppContext;
import com.eventplanner.app.util.FileUtil;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        SystemUser user = AppContext.getCurrentUser();

        String title = "Event Planner System";
        if (user != null) {
            title += "  |  Logged in: " + user.getUsername() + " (" + user.getRole() + ")";
        }
        setTitle(title);
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        applyModernUI();

        setJMenuBar(createMenuBar());

        JTabbedPane tabs = new JTabbedPane();

        tabs.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tabs.setBackground(new Color(30, 42, 86));
        tabs.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        UIManager.put("TabbedPane.selected", new Color(30, 42, 86));
        UIManager.put("TabbedPane.focus", new Color(30, 42, 86));
        UIManager.put("TabbedPane.unselectedBackground", new Color(30, 42, 86));

        tabs.addTab("CLIENTS", new ClientsPanel());
        tabs.addTab("EVENT_PACKAGES", new PackagesPanel());
        tabs.addTab("BOOKINGS", new BookingPanel());

        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            tabs.addTab("ADMIN", new AdminPanel());
        }

        add(tabs, BorderLayout.CENTER);
    }