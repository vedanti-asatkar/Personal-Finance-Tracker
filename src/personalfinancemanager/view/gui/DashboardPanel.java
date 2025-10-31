package personalfinancemanager.view.gui;

import java.awt.*;
import javax.swing.*;
import personalfinancemanager.auth.Session;
import personalfinancemanager.service.FinanceService;

public class DashboardPanel extends JPanel {

    private final MainFrame mainFrame;
    private final FinanceService financeService;

    public DashboardPanel(MainFrame mainFrame, FinanceService financeService) {
        this.mainFrame = mainFrame;
        this.financeService = financeService;

        setLayout(new BorderLayout());

        // Welcome message
        String username = Session.getUser() != null ? Session.getUser().getUsername() : "User";
        JLabel welcomeLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(welcomeLabel, BorderLayout.NORTH);

        // Main content area
        JPanel contentPanel = new JPanel();
        add(contentPanel, BorderLayout.CENTER);

        // Navigation panel
        JPanel navPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton viewTransactionsButton = new JButton("View Transactions");
        JButton addTransactionButton = new JButton("Add Transaction");
        JButton manageAccountsButton = new JButton("Manage Accounts");
        JButton manageCategoriesButton = new JButton("Manage Categories");
        JButton logoutButton = new JButton("Logout");

        navPanel.add(viewTransactionsButton);
        navPanel.add(addTransactionButton);
        navPanel.add(manageAccountsButton);
        navPanel.add(manageCategoriesButton);
        navPanel.add(logoutButton);

        add(navPanel, BorderLayout.WEST);

        // Action Listeners
        viewTransactionsButton.addActionListener(e -> {
            TransactionPanel txPanel = new TransactionPanel(financeService);
            contentPanel.removeAll();
            contentPanel.add(txPanel);
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        logoutButton.addActionListener(e -> {
            Session.logout();
            mainFrame.switchToPanel("LOGIN");
        });
    }
}