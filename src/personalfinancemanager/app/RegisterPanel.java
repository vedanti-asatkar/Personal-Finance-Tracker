package personalfinancemanager.view.gui;

import javax.swing.*;
import java.awt.*;
import personalfinancemanager.auth.AuthManager;
import personalfinancemanager.view.gui.MainFrame;

public class RegisterPanel extends JPanel {

    private final MainFrame mainFrame;
    private final AuthManager authManager;

    public RegisterPanel(MainFrame mainFrame, AuthManager authManager) {
        this.mainFrame = mainFrame;
        this.authManager = authManager;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Register New User");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton registerButton = new JButton("Register");
        JButton backToLoginButton = new JButton("Already have an account? Login");

        // Layout components
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1; add(new JLabel("Choose Username:"), gbc);
        gbc.gridy = 2; add(new JLabel("Choose Password:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1; add(usernameField, gbc);
        gbc.gridy = 2; add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        gbc.gridy = 4;
        add(backToLoginButton, gbc);

        // Action Listeners
        registerButton.addActionListener(e -> {
            RegistrationResult result = authManager.register(usernameField.getText(), new String(passwordField.getPassword()));
            switch (result) {
                case SUCCESS:
                    JOptionPane.showMessageDialog(this, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.switchToPanel("LOGIN");
                    break;
                case USER_ALREADY_EXISTS:
                    JOptionPane.showMessageDialog(this, "This username is already taken. Please choose another.", "Registration Failed", JOptionPane.WARNING_MESSAGE);
                    break;
                case DATABASE_ERROR:
                    JOptionPane.showMessageDialog(this, "Could not connect to the database. Please check your connection and configuration.", "Database Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });

        backToLoginButton.addActionListener(e -> mainFrame.switchToPanel("LOGIN"));
    }
}
