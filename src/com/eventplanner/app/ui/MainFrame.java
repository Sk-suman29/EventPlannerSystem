
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

        // Declare user ONLY ONCE
        SystemUser user = AppContext.getCurrentUser();

        String title = "Event Planner System";
        if (user != null) {
            title += " - Logged in as: " + user.getUsername() + " (" + user.getRole() + ")";
        }

        setTitle(title);
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setJMenuBar(createMenuBar());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Clients", new ClientsPanel());
        tabs.addTab("Event Packages", new PackagesPanel());
        tabs.addTab("Bookings", new BookingPanel());

        // Add Admin Tab ONLY if user role = ADMIN
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            tabs.addTab("Admin", new AdminPanel());
        }

        add(tabs, BorderLayout.CENTER);
    }
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Application menu
        JMenu menuApp = new JMenu("Application");
        JMenuItem itemLogout = new JMenuItem("Logout");
        JMenuItem itemExit = new JMenuItem("Exit");

        itemLogout.addActionListener(e -> logout());
        itemExit.addActionListener(e -> System.exit(0));

        menuApp.add(itemLogout);
        menuApp.addSeparator();
        menuApp.add(itemExit);

        // Data menu (save/load)
        JMenu menuData = new JMenu("Data");
        JMenuItem itemSave = new JMenuItem("Save All");
        JMenuItem itemLoad = new JMenuItem("Load All");

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
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        }
    }

    private void saveAllData() {
        ClientService cs = AppContext.getClientService();
        EventPackageService ps = AppContext.getPackageService();
        BookingService bs = AppContext.getBookingService();

        boolean ok1 = FileUtil.saveClients(cs, "clients.csv");
        boolean ok2 = FileUtil.savePackages(ps, "packages.csv");
        boolean ok3 = FileUtil.saveBookings(bs, "bookings.csv");

        if (ok1 && ok2 && ok3) {
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Some error occurred while saving.",
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAllData() {
        ClientService cs = AppContext.getClientService();
        EventPackageService ps = AppContext.getPackageService();
        BookingService bs = AppContext.getBookingService();

        boolean ok1 = FileUtil.loadClients(cs, "clients.csv");
        boolean ok2 = FileUtil.loadPackages(ps, "packages.csv");
        boolean ok3 = FileUtil.loadBookings(bs, cs, ps, "bookings.csv");

        if (ok1 && ok2 && ok3) {
            JOptionPane.showMessageDialog(this, "Data loaded successfully.\n" +
                    "You may need to reopen tabs or switch tabs to see updated tables.");
        } else {
            JOptionPane.showMessageDialog(this, "Some error occurred while loading.",
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



