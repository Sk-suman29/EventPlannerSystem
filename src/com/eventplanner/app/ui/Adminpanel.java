
package com.eventplanner.app.ui;

/*import com.eventplanner.app.service.BookingService;
import com.eventplanner.app.service.ClientService;
import com.eventplanner.app.service.EventPackageService;
import com.eventplanner.app.util.AppContext;
import com.eventplanner.app.util.FileUtil;

import javax.swing.*;
        import java.awt.*;

public class AdminPanel extends JPanel {

    private JLabel lblClientCount;
    private JLabel lblPackageCount;
    private JLabel lblBookingCount;

    public AdminPanel() {
        setLayout(new BorderLayout());

        // -------------------- SIDE MENU PANEL --------------------
        JPanel sideMenu = new JPanel();
        sideMenu.setPreferredSize(new Dimension(280, 0));
        sideMenu.setBackground(new Color(162, 164, 172));
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));

        JButton btnDashboard = createMenuButton("DASH_BOARD");
        JButton btnClients = createMenuButton("MANAGE_CLIENTS");
        JButton btnPackages = createMenuButton("MANAGE_PACKAGES");
        JButton btnBookings = createMenuButton("MANAGE_BOOKINGS");
        JButton btnExport = createMenuButton("EXPORT_REPORT");
        JButton btnLogout = createMenuButton("LOGOUT");

        sideMenu.add(btnDashboard);
        sideMenu.add(btnClients);
        sideMenu.add(btnPackages);
        sideMenu.add(btnBookings);
        sideMenu.add(Box.createVerticalGlue());
        sideMenu.add(btnExport);
        sideMenu.add(btnLogout);

        add(sideMenu, BorderLayout.WEST);

        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(new Color(255, 255, 255));

        JPanel statsPanel = new JPanel(new GridLayout(6, 1, 10, 0));
        statsPanel.setBorder(BorderFactory.createTitledBorder("SYSTEM STATISTICS"));
        statsPanel.setBackground(new Color(255, 255, 255));

        lblClientCount = new JLabel();
        lblPackageCount = new JLabel();
        lblBookingCount = new JLabel();

        Font statFont = new Font("SansSerif", Font.BOLD, 20);

        lblClientCount.setFont(statFont);
        lblPackageCount.setFont(statFont);
        lblBookingCount.setFont(statFont);

        lblClientCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblPackageCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblBookingCount.setHorizontalAlignment(SwingConstants.CENTER);

        statsPanel.add(lblClientCount);
        statsPanel.add(lblPackageCount);
        statsPanel.add(lblBookingCount);

        dashboard.add(statsPanel, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("REFRESH STATISTICS");
        btnRefresh.setBackground(new Color(30, 42, 86));
        btnRefresh.setForeground(Color.DARK_GRAY);
        btnRefresh.setFont(new Font("Monospaced", Font.BOLD, 16));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(new Color(255, 255, 255));
        bottom.add(btnRefresh);

        dashboard.add(bottom, BorderLayout.SOUTH);

        add(dashboard, BorderLayout.CENTER);

        btnRefresh.addActionListener(e -> refreshStats());
        btnExport.addActionListener(e -> exportReports());

        refreshStats();
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(250, 130));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(new Color(143, 163, 177));
        btn.setForeground(Color.DARK_GRAY);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Monospaced", Font.BOLD, 18));
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return btn;
    }

    private void refreshStats() {
        ClientService cs = AppContext.getClientService();
        EventPackageService ps = AppContext.getPackageService();
        BookingService bs = AppContext.getBookingService();

        lblClientCount.setText("Total Clients: " + cs.getClients().size());
        lblPackageCount.setText("Total Packages: " + ps.getPackages().size());
        lblBookingCount.setText("Total Bookings: " + bs.getBookings().size());
    }


    private void exportReports() {
        ClientService cs = AppContext.getClientService();
        EventPackageService ps = AppContext.getPackageService();
        BookingService bs = AppContext.getBookingService();

        boolean ok1 = FileUtil.saveClients(cs, "clients.csv");
        boolean ok2 = FileUtil.savePackages(ps, "packages.csv");
        boolean ok3 = FileUtil.saveBookings(bs, "bookings.csv");

        if (ok1 && ok2 && ok3) {
            JOptionPane.showMessageDialog(this,
                    "Reports exported to clients.csv, packages.csv, bookings.csv");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error exporting reports",
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}*/