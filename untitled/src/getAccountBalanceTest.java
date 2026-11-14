import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class getAccountBalanceTest {

    private static final double DELTA = 0.0001;

    private BankAccountManagementSystem bank;
    private ByteArrayOutputStream out;
    private PrintStream originalOut;

    @Before
    public void setUp() {
        bank = new BankAccountManagementSystem();
        originalOut = System.out;
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    // ---- getAccountBalance: Requirements 4.1 - 4.3 ----

    // Req 4.1 / statement + branch coverage: account exists (condition false -> skip if-body)
    @Test
    public void getAccountBalance_existingAccount_returnsInitialBalance() {
        bank.createAccount(1001, 500.0);
        assertEquals(500.0, bank.getAccountBalance(1001), DELTA);
    }

    // Req 4.1: existing account with zero balance is valid and not confused with "not found"
    @Test
    public void getAccountBalance_existingAccountZeroBalance_returnsZero() {
        bank.createAccount(1002, 0.0);
        assertEquals(0.0, bank.getAccountBalance(1002), DELTA);
    }

    // Req 4.1: balance reflects a deposit
    @Test
    public void getAccountBalance_afterDeposit_returnsUpdatedBalance() {
        bank.createAccount(1003, 100.0);
        bank.deposit(1003, 50.0);
        assertEquals(150.0, bank.getAccountBalance(1003), DELTA);
    }

    // Req 4.1: balance reflects a withdrawal
    @Test
    public void getAccountBalance_afterWithdraw_returnsUpdatedBalance() {
        bank.createAccount(1004, 100.0);
        bank.withdraw(1004, 40.0);
        assertEquals(60.0, bank.getAccountBalance(1004), DELTA);
    }

    // Req 4.1: correct account is returned when several exist
    @Test
    public void getAccountBalance_multipleAccounts_returnsBalanceOfRequestedAccount() {
        bank.createAccount(1, 10.0);
        bank.createAccount(2, 20.0);
        bank.createAccount(3, 30.0);
        assertEquals(20.0, bank.getAccountBalance(2), DELTA);
    }

    // Req 4.2 / 4.3 / statement + branch + condition coverage: account missing (condition true)
    @Test
    public void getAccountBalance_nonExistentAccount_returnsMinusOne() {
        assertEquals(-1.0, bank.getAccountBalance(9999), DELTA);
    }

    // Req 4.3: a message is displayed when the account does not exist
    @Test
    public void getAccountBalance_nonExistentAccount_displaysMessage() {
        bank.getAccountBalance(9999);
        String printed = out.toString();
        assertTrue(printed.contains("does not exist"));
        assertTrue(printed.contains("9999"));
    }

    // Req 4.3: no message is displayed when the account exists
    @Test
    public void getAccountBalance_existingAccount_displaysNoMessage() {
        bank.createAccount(1005, 75.0);
        bank.getAccountBalance(1005);
        assertEquals("", out.toString());
    }

    // Req 4.2: a different account number than the one created is still "not found"
    @Test
    public void getAccountBalance_otherAccountNumber_returnsMinusOne() {
        bank.createAccount(1006, 200.0);
        assertEquals(-1.0, bank.getAccountBalance(1007), DELTA);
    }

    // Req 4.2: lookup must not throw (previously a NullPointerException) on a missing account
    @Test
    public void getAccountBalance_emptySystem_doesNotThrow() {
        assertEquals(-1.0, bank.getAccountBalance(0), DELTA);
    }

    // Req 4.2: a failed createAccount (negative balance) leaves the account non-existent
    @Test
    public void getAccountBalance_accountCreationRejected_returnsMinusOne() {
        bank.createAccount(1008, -50.0);
        assertEquals(-1.0, bank.getAccountBalance(1008), DELTA);
    }
}
