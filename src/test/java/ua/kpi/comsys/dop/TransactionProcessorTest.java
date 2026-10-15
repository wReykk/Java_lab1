package ua.kpi.comsys.dop;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionProcessorTest {

    private final TransactionProcessor processor = new TransactionProcessor();

    @Test
    void testProcessNullTransaction() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            processor.process(null);
        });
        assertEquals("Transaction cannot be null", exception.getMessage());
    }

    @Test
    void testProcessDeposit() {
        Transaction tx = new Transaction.Deposit("ACC-001", new BigDecimal("500.00"));
        String result = processor.process(tx);

        assertTrue(result.contains("Transaction Type: DEPOSIT"));
        assertTrue(result.contains("Account: ACC-001"));
        assertTrue(result.contains("Amount: 500.00 USD"));
    }

    @Test
    void testProcessWithdrawal() {
        Transaction tx = new Transaction.Withdrawal("ACC-002", new BigDecimal("120.50"), "Food");
        String result = processor.process(tx);

        assertTrue(result.contains("Transaction Type: WITHDRAWAL"));
        assertTrue(result.contains("Account: ACC-002"));
        assertTrue(result.contains("Category: Food"));
        assertTrue(result.contains("Amount: 120.50 USD"));
    }

    @Test
    void testProcessTransfer() {
        Transaction tx = new Transaction.Transfer("ACC-001", "ACC-002", new BigDecimal("300.00"));
        String result = processor.process(tx);

        assertTrue(result.contains("Transaction Type: TRANSFER"));
        assertTrue(result.contains("From: ACC-001"));
        assertTrue(result.contains("To: ACC-002"));
        assertTrue(result.contains("Amount: 300.00 USD"));
    }

    @Test
    void testDepositValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Transaction.Deposit("", new BigDecimal("100")));
        assertThrows(IllegalArgumentException.class, () -> new Transaction.Deposit("ACC-1", BigDecimal.ZERO));
    }

    @Test
    void testWithdrawalValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Transaction.Withdrawal("   ", new BigDecimal("100"), "Food"));
        assertThrows(IllegalArgumentException.class, () -> new Transaction.Withdrawal("ACC-1", new BigDecimal("-10"), "Food"));
        assertThrows(IllegalArgumentException.class, () -> new Transaction.Withdrawal("ACC-1", new BigDecimal("100"), ""));
    }

    @Test
    void testTransferValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Transaction.Transfer(null, "ACC-2", new BigDecimal("50")));
        assertThrows(IllegalArgumentException.class, () -> new Transaction.Transfer("ACC-1", "", new BigDecimal("50")));
        assertThrows(IllegalArgumentException.class, () -> new Transaction.Transfer("ACC-1", "ACC-2", new BigDecimal("0")));
    }
}