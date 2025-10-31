package personalfinancemanager.view.gui;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import personalfinancemanager.auth.AuthManager;
import personalfinancemanager.auth.Session;
import personalfinancemanager.models.User;

public class LoginPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthManager authManager;

    public LoginPanel(MainFrame mainFrame, AuthManager authManager) {
        this.mainFrame = mainFrame;
        this.authManager = authManager;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Don't have an account? Register");

        // Layout components
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1; add(new JLabel("Username:"), gbc);
        gbc.gridy = 2; add(new JLabel("Password:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1; add(usernameField, gbc);
        gbc.gridy = 2; add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        gbc.gridy = 4;
        add(registerButton, gbc);

        // Action Listeners
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                User user = authManager.login(username, password);

                if (user != null) {
                    Session.setUser(user);
                    // Create and switch to dashboard using public methods from MainFrame
                    // Create and switch to dashboard using public methods
                    DashboardPanel dashboard = new DashboardPanel(mainFrame, mainFrame.getFinanceService());
                    mainFrame.addPanel(dashboard, "DASHBOARD");
                    mainFrame.switchToPanel("DASHBOARD");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException dbError) {
                 JOptionPane.showMessageDialog(this, "Could not connect to the database. Please check your connection and configuration.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> mainFrame.switchToPanel("REGISTER"));
    }
}