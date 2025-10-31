package personalfinancemanager.models;

import java.time.LocalDateTime;

public class Income extends Transaction {

    public Income(int transactionId, int accountId, int categoryId, double amount, LocalDateTime timestamp, String note, LocalDateTime createdAt) {
        super(transactionId, accountId, categoryId, amount, timestamp, note, createdAt);
    }

    @Override
    public String getType() {
        return "INCOME";
    }
}