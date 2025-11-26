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
    private void applyModernUI() {
        UIManager.put("MenuBar.background", new Color(37, 25, 3));
        UIManager.put("MenuBar.foreground", Color.lightGray);
        UIManager.put("Menu.foreground",Color.lightGray);
        UIManager.put("MenuItem.background", new Color(30, 42, 86));
        UIManager.put("MenuItem.foreground", Color.black);

        UIManager.put("TabbedPane.selected", new Color(83, 62, 24));
        UIManager.put("TabbedPane.contentOpaque", false);
        UIManager.put("TabbedPane.focus", new Color(143, 99, 15));
    }

    private JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(30, 42, 86));
        Color cream = new Color(30, 42, 86);
        menuBar.setForeground(cream);

        JMenu menuApp = new JMenu("APPLICATION");
        menuApp.setFont(new Font("Monospaced", Font.BOLD, 15));
        menuApp.setForeground(Color.black);

        JMenuItem itemLogout = new JMenuItem("Logout");
        JMenuItem itemExit = new JMenuItem("Exit");

        itemLogout.setBackground(new Color(30, 42, 86));
        itemLogout.setForeground(cream);

        itemExit.setBackground(new Color(30, 42, 86));
        itemExit.setForeground(cream);

        itemLogout.addActionListener(e -> logout());
        itemExit.addActionListener(e -> System.exit(0));

        menuApp.add(itemLogout);
        menuApp.addSeparator();
        menuApp.add(itemExit);

        JMenu menuData = new JMenu("DATA");
        menuData.setFont(new Font("Monospaced", Font.BOLD, 15));
        menuData.setForeground(Color.black);

        JMenuItem itemSave = new JMenuItem("Save All");
        JMenuItem itemLoad = new JMenuItem("Load All");

        itemSave.setBackground(new Color(30, 42, 86));
        itemSave.setForeground(cream);

        itemLoad.setBackground(new Color(30, 42, 86));
        itemLoad.setForeground(cream);

        itemSave.addActionListener(e -> saveAllData());
        itemLoad.addActionListener(e -> loadAllData());

        menuData.add(itemSave);
        menuData.add(itemLoad);

        menuBar.add(menuApp);
        menuBar.add(menuData);

        return menuBar;
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Do you really want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            AppContext.clearCurrentUser();
            dispose();
            new LoginFrame().setVisible(true);
        }
    }

    private void saveAllData() {

        boolean ok1 = FileUtil.saveClients(AppContext.getClientService(), "clients.csv");
        boolean ok2 = FileUtil.savePackages(AppContext.getPackageService(), "packages.csv");
        boolean ok3 = FileUtil.saveBookings(AppContext.getBookingService(), "bookings.csv");

        if (ok1 && ok2 && ok3) {
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Some error occurred while saving.",
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAllData() {

        boolean ok1 = FileUtil.loadClients(AppContext.getClientService(), "clients.csv");
        boolean ok2 = FileUtil.loadPackages(AppContext.getPackageService(), "packages.csv");
        boolean ok3 = FileUtil.loadBookings(
                AppContext.getBookingService(),
                AppContext.getClientService(),
                AppContext.getPackageService(),
                "bookings.csv"
        );

        if (ok1 && ok2 && ok3) {
            JOptionPane.showMessageDialog(this,
                    "Data loaded successfully.\nTabs may need to be refreshed.");
        } else {
            JOptionPane.showMessageDialog(this, "Error loading data.",
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}