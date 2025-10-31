package personalfinancemanager.models;

import java.time.LocalDateTime;

public abstract class Transaction {
    private int transactionId;
    private int accountId;
    private int categoryId;
    private double amount;
    private LocalDateTime timestamp;
    private String note;
    private LocalDateTime createdAt;

    public Transaction(int transactionId, int accountId, int categoryId, double amount, LocalDateTime timestamp, String note, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.note = note;
        this.createdAt = createdAt;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getType();

    // Getters
    public int getTransactionId() {
        return transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Transaction{" + "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", categoryId=" + categoryId +
                ", amount=" + amount +
                ", type=" + getType() + // Now uses the method
                ", timestamp=" + timestamp + '}';
    }
}