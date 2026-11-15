import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class getaccountbalancetest {

    private static final double DELTA = 0.0001;

    private BankAccountManagementSystem bank;

    @Before
    public void setUp() {
        bank = new BankAccountManagementSystem();
    }

    // ---- getAccountBalance: account EXISTS (all statements run, normal path) ----

    // Statement coverage: runs every line of the method, ending in the return
    @Test
    public void getAccountBalance_existingAccount_returnsInitialBalance() {
        bank.createAccount(1001, 500.0);
        assertEquals(500.0, bank.getAccountBalance(1001), DELTA);
    }

    // A zero balance is a valid balance and must come back as 0.0
    @Test
    public void getAccountBalance_existingAccountZeroBalance_returnsZero() {
        bank.createAccount(1002, 0.0);
        assertEquals(0.0, bank.getAccountBalance(1002), DELTA);
    }

    // Balance reflects a deposit
    @Test
    public void getAccountBalance_afterDeposit_returnsUpdatedBalance() {
        bank.createAccount(1003, 100.0);
        bank.deposit(1003, 50.0);
        assertEquals(150.0, bank.getAccountBalance(1003), DELTA);
    }

    // Balance reflects a withdrawal
    @Test
    public void getAccountBalance_afterWithdraw_returnsUpdatedBalance() {
        bank.createAccount(1004, 100.0);
        bank.withdraw(1004, 40.0);
        assertEquals(60.0, bank.getAccountBalance(1004), DELTA);
    }

    // The correct account is returned when several exist
    @Test
    public void getAccountBalance_multipleAccounts_returnsBalanceOfRequestedAccount() {
        bank.createAccount(1, 10.0);
        bank.createAccount(2, 20.0);
        bank.createAccount(3, 30.0);
        assertEquals(20.0, bank.getAccountBalance(2), DELTA);
    }

    // ---- getAccountBalance: account does NOT exist ----
    // The method has no existence check. accounts.get() returns null and
    // unboxing that null into a double throws a NullPointerException.

    @Test(expected = NullPointerException.class)
    public void getAccountBalance_nonExistentAccount_throwsNullPointerException() {
        bank.getAccountBalance(9999);
    }

    // A different number than the one created is still "not found"
    @Test(expected = NullPointerException.class)
    public void getAccountBalance_otherAccountNumber_throwsNullPointerException() {
        bank.createAccount(1006, 200.0);
        bank.getAccountBalance(1007);
    }

    // Empty system: nothing has been created at all
    @Test(expected = NullPointerException.class)
    public void getAccountBalance_emptySystem_throwsNullPointerException() {
        bank.getAccountBalance(0);
    }

    // A rejected createAccount (negative balance) leaves the account non-existent
    @Test(expected = NullPointerException.class)
    public void getAccountBalance_accountCreationRejected_throwsNullPointerException() {
        bank.createAccount(1008, -50.0);
        bank.getAccountBalance(1008);
    }
}