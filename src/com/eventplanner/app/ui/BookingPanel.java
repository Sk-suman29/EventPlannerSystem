package com.eventplanner.app.ui;

/* import com.eventplanner.app.model.Booking;
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

        // FORM

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

        // TABLE

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

        // SEARCH

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

    // HELPER METHODS

    private JTextField createInputField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Monospaced", Font.PLAIN, 14));
        field.setBackground(LIGHT_GRAY);
        field.setBorder(BorderFactory.createLineBorder(DARK_BLUE, 1));
        return field;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(CREAM);
        btn.setForeground(DARK_BLUE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        return btn;
    }

    private void loadComboData() {
        cbClient.removeAllItems();
        for (Client c : clientService.getClients()) cbClient.addItem(c);

        cbPackage.removeAllItems();
        for (EventPackage p : packageService.getPackages()) cbPackage.addItem(p);
    }

    private void createBooking() {
        try {
            String id = txtBookingId.getText().trim();
            String date = txtEventDate.getText().trim();

            Client client = (Client) cbClient.getSelectedItem();
            EventPackage pkg = (EventPackage) cbPackage.getSelectedItem();

            if (id.isEmpty() || date.isEmpty() || client == null || pkg == null)
                throw new IllegalArgumentException("All fields are required!");

            Booking b = new Booking(id, client, pkg, date);
            bookingService.createBooking(b);

            tableModel.addRow(new Object[]{
                    id, client.getName(), pkg.getName(), date
            });

            txtBookingId.setText("");
            txtEventDate.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Booking b : bookingService.getBookings()) {
            tableModel.addRow(new Object[]{
                    b.getBookingId(),
                    b.getClient().getName(),
                    b.getEventPackage().getName(),
                    b.getEventDate()
            });
        }
    }

    private void applyFilter() {
        String text = txtSearch.getText().trim();
        rowSorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("(?i)" + text));
    }

    private void resetFilter() {
        txtSearch.setText("");
        rowSorter.setRowFilter(null);
    }

    private void printInvoice() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Select a booking first!");
            return;
        }

        int row = table.convertRowIndexToModel(table.getSelectedRow());

        String bookingId = tableModel.getValueAt(row, 0).toString();
        String client = tableModel.getValueAt(row, 1).toString();
        String pkg = tableModel.getValueAt(row, 2).toString();
        String date = tableModel.getValueAt(row, 3).toString();

        String text =
                "======== EVENT INVOICE ========\n" +
                        "Booking ID : " + bookingId + "\n" +
                        "Client     : " + client + "\n" +
                        "Package    : " + pkg + "\n" +
                        "Event Date : " + date + "\n" +
                        "===============================\n" +
                        "Thank you for choosing our Event Planner System.";

        JOptionPane.showMessageDialog(this, text,
                "INVOICE - " + bookingId, JOptionPane.INFORMATION_MESSAGE);
    }
}*/
