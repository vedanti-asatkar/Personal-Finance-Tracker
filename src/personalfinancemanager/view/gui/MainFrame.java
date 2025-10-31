package personalfinancemanager.view.gui;

import java.awt.*;
import javax.swing.*;
import personalfinancemanager.auth.AuthManager;
import personalfinancemanager.service.FinanceService;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final AuthManager authManager;
    private final FinanceService financeService;

    public MainFrame() {
        this.authManager = new AuthManager();
        this.financeService = new FinanceService();

        setTitle("Personal Finance Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels
        LoginPanel loginPanel = new LoginPanel(this, authManager);
        RegisterPanel registerPanel = new RegisterPanel(this, authManager);

        // Add panels to the card layout
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");

        add(mainPanel);
        
        // The DashboardPanel will be created and added after successful login
    }

    public void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public FinanceService getFinanceService() {
        return financeService;
    }

    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }
}