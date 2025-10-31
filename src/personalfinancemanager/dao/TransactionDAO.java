package personalfinancemanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import personalfinancemanager.models.Expense;
import personalfinancemanager.models.Income;
import personalfinancemanager.models.Transaction;
import personalfinancemanager.util.DBUtil;

public class TransactionDAO extends AbstractDAO<Transaction> {

    @Override
    public boolean save(Transaction transaction) {
        String sql = "INSERT INTO transactions (account_id, category_id, amount, type, timestamp, note) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, transaction.getAccountId());
            pst.setInt(2, transaction.getCategoryId());
            pst.setDouble(3, transaction.getAmount());
            pst.setString(4, transaction.getType()); // Use the getType() method
            pst.setTimestamp(5, Timestamp.valueOf(transaction.getTimestamp()));
            pst.setString(6, transaction.getNote());

            int affectedRows = pst.executeUpdate();
            if (affectedRows == 1) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaction.setTransactionId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int transactionId) {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, transactionId);
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
            return false;
        }
    }

    public boolean updateTransaction(int transactionId, double newAmount, String newNote) {
        String sql = "UPDATE transactions SET amount = ?, note = ? WHERE transaction_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setDouble(1, newAmount);
            pst.setString(2, newNote);
            pst.setInt(3, transactionId);
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("Error updating transaction: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Transaction> findAllByUserId(int userId) {
        String sql = "SELECT t.*, a.user_id FROM transactions t JOIN accounts a ON t.account_id = a.account_id WHERE a.user_id = ?";
        return findTransactions(sql, userId);
    }

    public List<Transaction> findByMonth(int userId, int year, int month) {
        String sql = "SELECT t.* FROM transactions t JOIN accounts a ON t.account_id = a.account_id " +
                     "WHERE a.user_id = ? AND YEAR(t.timestamp) = ? AND MONTH(t.timestamp) = ?";
        return findTransactions(sql, userId, year, month);
    }

    private List<Transaction> findTransactions(String sql, Object... params) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection con = DBUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching transactions: " + e.getMessage());
        }
        return transactions;
    }

    public double getTotalByType(int userId, String type) {
        String sql = "SELECT SUM(t.amount) FROM transactions t JOIN accounts a ON t.account_id = a.account_id " +
                     "WHERE a.user_id = ? AND t.type = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, userId);
            pst.setString(2, type);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting total by type: " + e.getMessage());
        }
        return 0.0;
    }

    private Transaction mapRowToTransaction(ResultSet rs) throws SQLException {
        int id = rs.getInt("transaction_id");
        int accountId = rs.getInt("account_id");
        int categoryId = rs.getInt("category_id");
        double amount = rs.getDouble("amount");
        String type = rs.getString("type");
        Timestamp timestamp = rs.getTimestamp("timestamp");
        String note = rs.getString("note");
        // The created_at field is not always selected, so handle it safely.

        if ("INCOME".equalsIgnoreCase(type)) {
            return new Income(id, accountId, categoryId, amount, timestamp.toLocalDateTime(), note, null); // Pass null for createdAt
        } else {
            return new Expense(id, accountId, categoryId, amount, timestamp.toLocalDateTime(), note, null); // Pass null for createdAt
        }
    }
}