package ua.kpi.comsys.dop;

public class TransactionProcessor {

    public String process(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        return switch (transaction) {
            case Transaction.Deposit d -> """
                    Transaction Type: DEPOSIT
                    Account: %s
                    Amount: %s USD""".formatted(d.accountId(), d.amount());

            case Transaction.Withdrawal w -> """
                    Transaction Type: WITHDRAWAL
                    Account: %s
                    Category: %s
                    Amount: %s USD""".formatted(w.accountId(), w.category(), w.amount());

            case Transaction.Transfer t -> """
                    Transaction Type: TRANSFER
                    From: %s
                    To: %s
                    Amount: %s USD""".formatted(t.fromAccount(), t.toAccount(), t.amount());
        };
    }
}