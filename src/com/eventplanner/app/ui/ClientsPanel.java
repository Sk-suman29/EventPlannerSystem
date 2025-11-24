package com.eventplanner.app.ui;

import com.eventplanner.app.model.Client;
import com.eventplanner.app.service.ClientService;
import com.eventplanner.app.util.AppContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ClientsPanel extends JPanel {

    private final ClientService clientService;
    private final DefaultTableModel tableModel;

    private JTextField txtId, txtName, txtPhone;
    private JTextField txtSearch;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private JTable table;

    // Colors
    private final Color DARK_BLUE = new Color(17, 29, 71);
    private final Color CREAM = new Color(241, 240, 238);
    private final Color LIGHT_GRAY = new Color(196, 207, 202);

    public ClientsPanel() {
        clientService = AppContext.getClientService();

        setLayout(new BorderLayout());
        setBackground(DARK_BLUE);

        // ---------- FORM PANEL ----------
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder(15, 15, 15, 15),
                BorderFactory.createTitledBorder("CLIENT DETAILS")));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = createInputField();
        txtName = createInputField();
        txtPhone = createInputField();

        JButton btnAdd = createButton("ADD");
        JButton btnUpdate = createButton("UPDATE");
        JButton btnDelete = createButton("DELETE");
        JButton btnClear = createButton("CLEAR");

        // Row 1 - ID
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        form.add(txtId, gbc);

        // Row 2 - Name
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("NAME:"), gbc);
        gbc.gridx = 1;
        form.add(txtName, gbc);

        // Row 3 - Phone
        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("TELEPHONE NO.:"), gbc);
        gbc.gridx = 1;
        form.add(txtPhone, gbc);

        // Buttons panel
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttons.setBackground(CREAM);
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        form.add(buttons, gbc);

        add(form, BorderLayout.NORTH);

        // ---------- TABLE PANEL ----------
        tableModel = new DefaultTableModel(new Object[]{"ID", "NAME", "TELEPHONE NO."}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(Color.lightGray);
        table.setGridColor(Color.DARK_GRAY);
        table.getTableHeader().setBackground(CREAM);
        table.getTableHeader().setForeground(DARK_BLUE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("CLIENTS"));

        add(scroll, BorderLayout.CENTER);

        // ---------- SEARCH PANEL ----------
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(CREAM);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder("Search")
        ));

        txtSearch = createInputField();
        txtSearch.setPreferredSize(new Dimension(200, 30));

        JButton btnSearch = createButton("Search");
        JButton btnReset = createButton("Reset");

        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);

        add(searchPanel, BorderLayout.SOUTH);

        // load db data
        refreshTable();

        // ---------- Listeners ----------
        btnAdd.addActionListener(e -> addClient());
        btnUpdate.addActionListener(e -> updateClient());
        btnDelete.addActionListener(e -> deleteClient());
        btnClear.addActionListener(e -> clearFields());

        btnSearch.addActionListener(e -> applyFilter());
        btnReset.addActionListener(e -> resetFilter());

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.convertRowIndexToModel(table.getSelectedRow());
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtPhone.setText(tableModel.getValueAt(row, 2).toString());
            }
        });
    }

    // ---------- UI Helpers ----------
    private JTextField createInputField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Monospaced", Font.PLAIN, 14));
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
        btn.setPreferredSize(new Dimension(90, 30));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return btn;
    }
}