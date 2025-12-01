## 🧩 Project Structure
Perfect ✅ — here’s a professional and polished README.md file you can directly upload with your Java + MySQL Banking System project on GitHub.

You can copy this as README.md in your project root folder.


---

# 💰 Secure Banking System using Java and MySQL

A **console-based banking application** built using **Java** and **MySQL** that simulates core banking functionalities such as account creation, login, deposit, withdrawal, transfer, and transaction history — with proper validation and data security mechanisms.

---

## 🚀 Features

- 🔹 **Create Account** (Savings / Current)
- 🔹 **Login System** with password validation
- 🔹 **Deposit & Withdraw Money**
- 🔹 **Fund Transfer** between accounts (with OTP verification)
- 🔹 **Transaction History** with timestamps
- 🔹 **Account Balance Check**
- 🔹 **Auto-generated Transaction IDs**
- 🔹 **Data Persistence** using MySQL
- 🔹 **Input Validation & Error Handling**
- 🔹 **Secure Password Policy**

---

## 🧩 Project Structure

Banking_System/ │ ├── src/ │   ├── Account.java          # Defines the account structure and attributes │   ├── DBConnection.java     # Handles MySQL database connectivity │   └── Main.java             # Main application logic (UI + operations) │ ├── README.md                 # Project documentation └── bankdb.sql                # SQL file for creating database tables

---

## ⚙️ Technologies Used

| Category | Technology |
|-----------|-------------|
| Language | Java (JDK 17 or higher) |
| Database | MySQL |
| Connectivity | JDBC |
| IDE | IntelliJ IDEA / Eclipse / VS Code |
| Libraries | java.sql, java.time, java.math.BigDecimal |

---

## 🗄️ Database Schema

### 1. **accounts** Table
| Column | Type | Description |
|--------|------|-------------|
| account_number | VARCHAR(20) | Primary key |
| holder_name | VARCHAR(100) | Account holder name |
| email | VARCHAR(100) | Unique email ID |
| phone | VARCHAR(15) | Unique phone number |
| ifsc | VARCHAR(20) | Bank IFSC code |
| password | VARCHAR(255) | Encrypted password |
| balance | DECIMAL(15,2) | Current account balance |
| account_type | VARCHAR(20) | Savings / Current |
| created_at | TIMESTAMP | Account creation date |

### 2. **transactions** Table
| Column | Type | Description |
|--------|------|-------------|
| txn_id | VARCHAR(20) | Unique transaction ID |
| account_number | VARCHAR(20) | Linked account number |
| type | VARCHAR(50) | Deposit / Withdraw / Transfer |
| amount | DECIMAL(15,2) | Transaction amount |
| details | VARCHAR(255) | Transaction notes |
| timestamp | TIMESTAMP | Time of transaction |

---

## 🛠️ Setup Instructions

### Step 1: Clone the Repository
```bash
git clone https://github.com/<your-username>/Secure-Banking-System.git

Step 2: Configure MySQL Database

1. Create a database named bankdb

CREATE DATABASE bankdb;
USE bankdb;


2. Run the following table creation scripts:

CREATE TABLE accounts (
    account_number VARCHAR(20) PRIMARY KEY,
    holder_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL,
    ifsc VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance DECIMAL(15,2) NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transactions (
    txn_id VARCHAR(20) PRIMARY KEY,
    account_number VARCHAR(20),
    type VARCHAR(50),
    amount DECIMAL(15,2),
    details VARCHAR(255),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);



Step 3: Update Database Credentials

In DBConnection.java, update the following lines with your credentials:

private static final String URL = "jdbc:mysql://localhost:3306/bankdb";
private static final String USER = "root";
private static final String PASSWORD = "yourpassword";

Step 4: Compile and Run

Compile the program:

javac src/*.java

Run the application:

java src/Main


---

🧠 Key Learning Outcomes

Understanding JDBC and database integration in Java.

Implementation of transactions, commits, and rollbacks.

Hands-on with Object-Oriented Programming (OOP) principles.

Building secure input validation and data persistence logic.

Designing a simple real-world banking simulation system.



---

🔐 Security Features

Password must include:

Minimum 15 characters

Uppercase, lowercase, numbers, and symbols


OTP verification during fund transfer.

Database rollback on failed transactions.

Email and phone number validation.



---

🌱 Future Enhancements

🔸 Add GUI using JavaFX or Swing

🔸 Implement password hashing (SHA-256 / bcrypt)

🔸 Add email/SMS notifications

🔸 Create an admin panel for account management

🔸 Support for interest calculation in savings accounts



---

🧑‍💻 Author

Jambhava Dattudu
💼 Internship Project – Secure Banking System
📧 jambhava76@gmail.com
🌐 GitHub Profile - https://github.com/Jambhava76


---

🏁 Conclusion

The Secure Banking System project successfully demonstrates how Java and MySQL can be integrated to build a functional, secure, and user-friendly digital banking platform.
It highlights practical use of OOP concepts, JDBC connectivity, and data validation — making it a solid foundation for real-world financial applications.


---
