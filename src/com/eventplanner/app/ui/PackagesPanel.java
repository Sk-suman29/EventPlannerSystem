package com.eventplanner.app.ui;

import com.eventplanner.app.model.EventPackage;
import com.eventplanner.app.model.SystemUser;
import com.eventplanner.app.service.EventPackageService;
import com.eventplanner.app.util.AppContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class PackagesPanel extends JPanel {

    private final EventPackageService packageService;
    private final DefaultTableModel tableModel;

    private JTextField txtId, txtCategory, txtName, txtPrice;
    private JTextField txtSearch;
    private JButton btnAdd;
    private TableRowSorter<DefaultTableModel> rowSorter;

    // colors
    private final Color DARK_BLUE = new Color(17, 29, 71);
    private final Color CREAM = new Color(241, 240, 238);
    private final Color LIGHT_GRAY = new Color(196, 207, 202);

    public PackagesPanel() {
        packageService = AppContext.getPackageService();

        setLayout(new BorderLayout());
        setBackground(DARK_BLUE);

        // FORM
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15),
                BorderFactory.createTitledBorder("EVENT PACKAGE DETAILS")
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = createInputField();
        txtCategory = createInputField();
        txtName = createInputField();
        txtPrice = createInputField();

        btnAdd = createButton("ADD");

        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        form.add(txtId, gbc);

        // Category
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("CATEGORY:"), gbc);
        gbc.gridx = 1;
        form.add(txtCategory, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("PACKAGE NAME:"), gbc);
        gbc.gridx = 1;
        form.add(txtName, gbc);

        // Price
        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("PRICE:"), gbc);
        gbc.gridx = 1;
        form.add(txtPrice, gbc);

        // Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(CREAM);
        btnPanel.add(btnAdd);
        form.add(btnPanel, gbc);

        add(form, BorderLayout.NORTH);

        // TABLE
        tableModel = new DefaultTableModel(new Object[]{"ID", "CATEGORY", "NAME", "PRICE"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(LIGHT_GRAY);
        table.setGridColor(Color.DARK_GRAY);
        table.getTableHeader().setBackground(CREAM);
        table.getTableHeader().setForeground(DARK_BLUE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("EVENT PACKAGES"));

        add(scroll, BorderLayout.CENTER);

        // SEARCH PANEL
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(CREAM);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder("Search")
        ));

        txtSearch = createInputField();
        txtSearch.setPreferredSize(new Dimension(220, 30));

        JButton btnSearch = createButton("Search");
        JButton btnReset = createButton("Reset");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);

        add(searchPanel, BorderLayout.SOUTH);

        refreshTable();

        btnAdd.addActionListener(e -> addPackage());
        btnSearch.addActionListener(e -> applyFilter());
        btnReset.addActionListener(e -> resetFilter());

        applyRoleBasedAccess();
    }

    // UI HELPERS
    private JTextField createInputField() {
        JTextField field = new JTextField(30); // LENGTH INCREASED
        field.setFont(new Font("Monospaced", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 30));
        field.setBorder(BorderFactory.createLineBorder(DARK_BLUE, 1));
        field.setBackground(LIGHT_GRAY);
        return field;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(CREAM);
        btn.setForeground(DARK_BLUE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(100, 32));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return btn;
    }

    // LOGIC
    private void applyRoleBasedAccess() {
        SystemUser user = AppContext.getCurrentUser();
        boolean isAdmin = user != null && "ADMIN".equalsIgnoreCase(user.getRole());

        if (!isAdmin) {
            txtId.setEnabled(false);
            txtCategory.setEnabled(false);
            txtName.setEnabled(false);
            txtPrice.setEnabled(false);
            btnAdd.setEnabled(false);
            btnAdd.setToolTipText("Only ADMIN users can add packages.");
        }
    }

    private void addPackage() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String category = txtCategory.getText().trim();
            String name = txtName.getText().trim();
            double price = Double.parseDouble(txtPrice.getText().trim());

            if (category.isEmpty() || name.isEmpty())
                throw new IllegalArgumentException("Category and name cannot be empty");

            EventPackage ep = new EventPackage(id, category, name, price);
            packageService.addPackage(ep);

            tableModel.addRow(new Object[]{id, category, name, price});
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (EventPackage p : packageService.getPackages()) {
            tableModel.addRow(new Object[]{p.getId(), p.getCategory(), p.getName(), p.getPrice()});
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtCategory.setText("");
        txtName.setText("");
        txtPrice.setText("");
    }

    private void applyFilter() {
        String text = txtSearch.getText().trim();
        rowSorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("(?i)" + text));
    }

    private void resetFilter() {
        txtSearch.setText("");
        rowSorter.setRowFilter(null);
    }
}



