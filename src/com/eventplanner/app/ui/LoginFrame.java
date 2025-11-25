package com.eventplanner.app.ui;

import com.eventplanner.app.exceptions.AuthenticationException;
import com.eventplanner.app.model.SystemUser;
//import com.eventplanner.app.util.AppContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Login to Event Planner System");
        setSize(450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
    }

    private void initUI() {

        // ------------------ HEADER ------------------
        JPanel header = new JPanel();
        header.setBackground(new Color(30, 42, 86));
        JLabel lblTitle = new JLabel("MiniBloom Events");
        lblTitle.setForeground(Color.lightGray);
        lblTitle.setFont(new Font("Monospaced", Font.BOLD, 24));
        header.add(lblTitle);
        add(header, BorderLayout.NORTH);

        // ------------------ MAIN CARD PANEL ------------------

        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.setBackground(Color.lightGray);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblUsername = new JLabel("USERNAME:");
        lblUsername.setFont(new Font("Monospaced", Font.BOLD, 20)); // Increase size here
        lblUsername.setForeground(Color.black);
        card.add(lblUsername, gbc);

        // Username Field
        gbc.gridx = 1;
        txtUsername = new JTextField(30);
        txtUsername.setFont(new Font("Monospaced", Font.BOLD, 16));
        card.add(txtUsername, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblPassword = new JLabel("PASSWORD:");
        lblPassword.setFont(new Font("Monospaced", Font.BOLD, 20));
        lblPassword.setForeground(Color.black);
        card.add(lblPassword, gbc);

        // Password Field
        gbc.gridx = 1;
        txtPassword = new JPasswordField(30);
        txtPassword.setFont(new Font("Monospaced", Font.BOLD, 16));
        card.add(txtPassword, gbc);

        // Show Password
        gbc.gridx = 1;
        gbc.gridy = 2;
        JCheckBox chkShow = new JCheckBox("Show Password");
        chkShow.addActionListener(e -> {
            if (chkShow.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });
        card.add(chkShow, gbc);

        // ------------------ BUTTON PANEL ------------------
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(Color.lightGray);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setBackground(new Color(30, 42, 86));
        btnLogin.setForeground(Color.black);
        btnLogin.setFont(new Font("Monospaced", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(80, 35));

        JButton btnExit = new JButton("EXIT");
        btnExit.setBackground(new Color(30, 42, 86));
        btnExit.setForeground(Color.black);
        btnExit.setFont(new Font("Monospaced", Font.BOLD, 14));
        btnExit.setFocusPainted(false);
        btnExit.setPreferredSize(new Dimension(80, 35));

        btnPanel.add(btnLogin);
        btnPanel.add(btnExit);

        // Add buttons under card panel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        card.add(btnPanel, gbc);

        // Add card to center
        add(card, BorderLayout.CENTER);

        // Event handlers
        btnLogin.addActionListener(e -> doLogin());
        btnExit.addActionListener(e -> System.exit(0));

        // ENTER key triggers login
        getRootPane().setDefaultButton(btnLogin);
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter username and password",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            //SystemUser user = AppContext.getAuthService().authenticate(username, password);
            //AppContext.setCurrentUser(user);

            //new MainFrame().setVisible(true);
            dispose();

        //} catch (AuthenticationException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        //}
    //}

   // public static void main(String[] args) {
     //   try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ignore) {}

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
