# 📘 Personal Finance Manager App (Console-Based Java & MySQL Application)


## 📄 Project Overview

The **Personal Finance Manager** is a modular, menu-driven Java console application built to help individuals track, analyze, and manage their finances across multiple accounts and categories. Developed with JDBC and backed by MySQL, it provides a structured workflow for users to log transactions, monitor budgets, and export financial summaries—all in an offline, framework-free environment.

---

## 🎯 Objectives

- Design a clear, interactive interface for personal finance operations  
- Enable CRUD operations on accounts, categories, and transactions  
- Support monthly budget goals and detect overspending with alerts  
- Provide insight-rich financial reports (top categories, net savings, etc.)  
- Offer `.csv` export of transaction logs and breakdown summaries  
- Promote modular and scalable backend architecture in Java  

---

## 🧱 System Architecture

### 🔹 Layered Design

| Layer       | Description                                        |
|-------------|----------------------------------------------------|
| `auth`      | Registration, login logic, session handling        |
| `dao`       | JDBC-based data access for User, Account, Category, Transaction, Budget |
| `models`    | Plain Old Java Objects (POJOs) representing data entities |
| `service`   | Business logic: analytics, validation, budgeting   |
| `view`      | Console menus, reports, user interactions          |
| `util`      | Input handling, DB connection, hashing, date tools |

---

## 🧪 Functional Modules

| Module                | Responsibilities                                                   |
|-----------------------|--------------------------------------------------------------------|
| Authentication        | User registration, login, and session tracking                    |
| Account Management    | Add, list, or delete financial accounts per user                  |
| Category Management   | Add/view/delete categories (e.g., Groceries, Salary, Utilities)   |
| Transactions          | Record/edit/remove income & expenses with notes & timestamps      |
| Budget Goals & Alerts | Set monthly budget limits and receive overspending warnings       |
| Reports & Insights    | Show net savings, top categories, and monthly summaries           |
| CSV Export            | Export transaction history and budget summaries to `.csv`         |

---

## 📂 Database Schema

**Database Name:** `personal_finance_db`

```sql
-- 1. Users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Accounts table
CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    balance DECIMAL(12, 2) DEFAULT 0.00 CHECK (balance >= 0),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Categories table
CREATE TABLE categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (user_id, name),  -- Prevent duplicate categories per user
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Transactions table
CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    category_id INT NOT NULL,
    amount DECIMAL(12, 2) NOT NULL CHECK (amount > 0),
    type ENUM('INCOME', 'EXPENSE') NOT NULL,
    timestamp DATETIME,
    note VARCHAR(255),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Budgets table
CREATE TABLE budgets (
    budget_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    year INT NOT NULL,
    month INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (user_id, year, month) -- prevent duplicates
);
```

---

## 🖥️ Sample Output (Console Snapshot)

```
=== Personal Finance Manager ===
1. Login
2. Register
3. Exit

=== Dashboard ===
1. View Transactions
2. Add Transaction
3. View Top Categories
4. View Net Savings
5. Set Monthly Budget
6. Export to CSV
7. Manage Accounts
8. Manage Categories
9. Logout
```

---

## 🧑‍💻 How to Run

1. Install **Java 17+** and **MySQL Server**
2. Create the database and tables using the provided `schema.sql`
3. Configure your database credentials in `DBUtil.java`
4. Compile and launch the app:
   
   For Windows (PowerShell):
   ```powershell
   # Compile all .java files (replace with your actual JAR path)
   javac -d bin -cp "path\to\mysql-connector-j-8.3.0.jar" @(Get-ChildItem -Path src -Recurse -Filter *.java).FullName
   
   # Run the application
   java -cp "bin;path\to\mysql-connector-j-8.3.0.jar" personalfinancemanager.app.FinanceApp
```

---

## 🧾 CSV Export Example
A sample file will be saved inside the exports/ directory as:
```
transactions_20250701_113212.csv
```
Example contents:
```
TransactionID,Type,Amount,CategoryID,Timestamp,Note
1,EXPENSE,1200.00,3,2025-07-01 10:21:00,Monthly Electricity Bill
2,INCOME,5000.00,1,2025-07-01 09:00:00,Salary Credit
```

---

## 🤝 Contributing

Pull requests are welcome! Feel free to fork the repository and submit improvements.

**Contributions are welcome! Follow these steps:**
1. Fork the project.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature description"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---

## 📧 Contact
For queries or suggestions:
- 📩 Email: [spreveen123@gmail.com](mailto:spreveen123@gmail.com)
- 🌐 LinkedIn: [www.linkedin.com/in/preveen-s-17250529b/](https://www.linkedin.com/in/preveen-s-17250529b/)

---

## 🌟 Show Your Support
If you like this project, please consider giving it a ⭐ on GitHub!
