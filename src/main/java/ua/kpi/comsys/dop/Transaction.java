package ua.kpi.comsys.dop;

import java.math.BigDecimal;

public sealed interface Transaction permits Transaction.Deposit, Transaction.Withdrawal, Transaction.Transfer {

    record Deposit(String accountId, BigDecimal amount) implements Transaction {
        public Deposit {
            if (accountId == null || accountId.isBlank()) {
                throw new IllegalArgumentException("Account ID cannot be null or blank");
            }
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }
        }
    }

    record Withdrawal(String accountId, BigDecimal amount, String category) implements Transaction {
        public Withdrawal {
            if (accountId == null || accountId.isBlank()) {
                throw new IllegalArgumentException("Account ID cannot be null or blank");
            }
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }
            if (category == null || category.isBlank()) {
                throw new IllegalArgumentException("Category cannot be null or blank");
            }
        }
    }

    record Transfer(String fromAccount, String toAccount, BigDecimal amount) implements Transaction {
        public Transfer {
            if (fromAccount == null || fromAccount.isBlank()) {
                throw new IllegalArgumentException("Source account cannot be null or blank");
            }
            if (toAccount == null || toAccount.isBlank()) {
                throw new IllegalArgumentException("Target account cannot be null or blank");
            }
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }
        }
    }
}