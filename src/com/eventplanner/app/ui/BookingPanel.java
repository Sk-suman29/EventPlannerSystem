package com.eventplanner.app.ui;

import com.eventplanner.app.model.Booking;
import com.eventplanner.app.model.Client;
import com.eventplanner.app.model.EventPackage;
import com.eventplanner.app.service.BookingService;
import com.eventplanner.app.service.ClientService;
import com.eventplanner.app.service.EventPackageService;
import com.eventplanner.app.util.AppContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class BookingPanel extends JPanel {

    private final BookingService bookingService;
    private final ClientService clientService;
    private final EventPackageService packageService;

    private JComboBox<Client> cbClient;
    private JComboBox<EventPackage> cbPackage;
    private JTextField txtBookingId, txtEventDate;

    private JTextField txtSearch;
    private JTable table;

    private final DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;

    // Colors
    private final Color DARK_BLUE = new Color(17, 29, 71);
    private final Color CREAM = new Color(241, 240, 238);
    private final Color LIGHT_GRAY = new Color(196, 207, 202);

    public BookingPanel() {

        bookingService = AppContext.getBookingService();
        clientService = AppContext.getClientService();
        packageService = AppContext.getPackageService();

        setLayout(new BorderLayout());
        setBackground(DARK_BLUE);

        // FORM PANEL (TOP)

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15),
                BorderFactory.createTitledBorder("BOOKING DETAILS")
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtBookingId = createInputField();
        txtEventDate = createInputField();

        cbClient = new JComboBox<>();
        cbClient.setFont(new Font("Monospaced", Font.PLAIN, 14));

        cbPackage = new JComboBox<>();
        cbPackage.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JButton btnReload = createButton("RELOAD LISTS");
        JButton btnCreate = createButton("CREATE");

        // Booking ID
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("BOOKING ID:"), gbc);
        gbc.gridx = 1;
        form.add(txtBookingId, gbc);

        // Client
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("CLIENT:"), gbc);
        gbc.gridx = 1;
        form.add(cbClient, gbc);

        // Package
        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("PACKAGE:"), gbc);
        gbc.gridx = 1;
        form.add(cbPackage, gbc);

        // Date
        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("EVENT DATE:"), gbc);
        gbc.gridx = 1;
        form.add(txtEventDate, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnPanel.setBackground(CREAM);
        btnPanel.add(btnReload);
        btnPanel.add(btnCreate);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        form.add(btnPanel, gbc);

        add(form, BorderLayout.NORTH);

        // TABLE PANEL

        tableModel = new DefaultTableModel(
                new Object[]{"BOOKING ID", "CLIENT", "PACKAGE", "EVENT DATE"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setBackground(Color.LIGHT_GRAY);
        table.setGridColor(Color.DARK_GRAY);

        table.getTableHeader().setBackground(CREAM);
        table.getTableHeader().setForeground(DARK_BLUE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("BOOKINGS LIST"));
        add(scroll, BorderLayout.CENTER);

        // SEARCH PANEL

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(CREAM);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder("SEARCH / INVOICE")
        ));

        txtSearch = createInputField();
        txtSearch.setPreferredSize(new Dimension(200, 30));

        JButton btnSearch = createButton("SEARCH");
        JButton btnReset = createButton("RESET");
        JButton btnInvoice = createButton("INVOICE");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);
        searchPanel.add(btnInvoice);

        add(searchPanel, BorderLayout.SOUTH);

        loadComboData();
        refreshTable();

        btnReload.addActionListener(e -> loadComboData());
        btnCreate.addActionListener(e -> createBooking());
        btnSearch.addActionListener(e -> applyFilter());
        btnReset.addActionListener(e -> resetFilter());
        btnInvoice.addActionListener(e -> printInvoice());



    }
}
