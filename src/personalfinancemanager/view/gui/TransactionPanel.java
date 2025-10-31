package personalfinancemanager.view.gui;

import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import personalfinancemanager.auth.Session;
import personalfinancemanager.models.Transaction;
import personalfinancemanager.service.FinanceService;

public class TransactionPanel extends JPanel {

    private final FinanceService financeService;
    private JTable transactionTable;
    private TransactionTableModel tableModel;

    public TransactionPanel(FinanceService financeService) {
        this.financeService = financeService;
        setLayout(new BorderLayout());

        List<Transaction> transactions = financeService.getAllTransactions(Session.getUser().getUserId());
        tableModel = new TransactionTableModel(transactions);
        transactionTable = new JTable(tableModel);
        transactionTable.setAutoCreateRowSorter(true); // Enable sorting

        // --- Professional Formatting for Currency ---
        // This renderer will format any Double value as currency.
        DefaultTableCellRenderer currencyRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Double) {
                    // Use Locale for India to get the Rupee symbol format
                    value = NumberFormat.getCurrencyInstance(new java.util.Locale("en", "IN")).format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        transactionTable.getColumnModel().getColumn(2).setCellRenderer(currencyRenderer); // Apply to "Amount" column
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);
    }
}