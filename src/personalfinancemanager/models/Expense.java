package personalfinancemanager.models;

import java.time.LocalDateTime;

public class Expense extends Transaction {

    public Expense(int transactionId, int accountId, int categoryId, double amount, LocalDateTime timestamp, String note, LocalDateTime createdAt) {
        super(transactionId, accountId, categoryId, amount, timestamp, note, createdAt);
    }

    @Override
    public String getType() {
        return "EXPENSE";
    }
}