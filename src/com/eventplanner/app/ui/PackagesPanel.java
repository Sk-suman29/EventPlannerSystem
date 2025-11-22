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

    public PackagesPanel() {
        packageService = AppContext.getPackageService();

        setLayout(new BorderLayout());

        // Form
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Add Event Package"));

        txtId = new JTextField();
        txtCategory = new JTextField();
        txtName = new JTextField();
        txtPrice = new JTextField();
        btnAdd = new JButton("Add Package");

        form.add(new JLabel("ID:"));           form.add(txtId);
        form.add(new JLabel("Category:"));     form.add(txtCategory);
        form.add(new JLabel("Package Name:")); form.add(txtName);
        form.add(new JLabel("Price:"));        form.add(txtPrice);
        form.add(new JLabel(""));              form.add(btnAdd);

        add(form, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Category", "Name", "Price"}, 0);
        JTable table = new JTable(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Event Packages"));

        add(scroll, BorderLayout.CENTER);


}
}