import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

/** Combined JUnit 5 tests: create account, deposit, withdrawal, and get account balance. */
public class Grp6_BankAccountSystemTest {
    private static final double DELTA = 0.0001;
    private Grp6_BankAccountManagementSystem bank;

    @BeforeEach
    void setUp() {
        bank = new Grp6_BankAccountManagementSystem();
    }

// ===== CREATE ACCOUNT =====
@Test
    public void testCreateAccount() {

        Grp6_BankAccountManagementSystem bank = new Grp6_BankAccountManagementSystem();

// Test 1: Create an account with a positive balance
        assertTrue(bank.createAccount(12345, 1.0));

// Test 2: Attempt to create a duplicate account
        assertFalse(bank.createAccount(12345, 0.0));

// Test 3: Attempt to create an account with a negative balance
        assertFalse(bank.createAccount(234567, -1.0));

// Test 4: Create an account with a zero balance
        assertTrue(bank.createAccount(234567, 0.0));
    }

// ===== DEPOSIT =====
// Test 1: Deposit into a non-existing account
    @Test
    void testDepositNonExistingAcc() {
        double result = bank.deposit(2347, 85.0);
        assertEquals(-1.0, result, 0.001);
    }

    //Test 2: Deposit into an existing account
    @Test
    void testDepositExistingAcc() {
        bank.createAccount(2155, 100.0);
        
        double result = bank.deposit(2155, 78.2);
        
        assertEquals(178.2, result, 0.001);
        assertEquals(178.2, bank.getAccountBalance(2155), 0.001);
    }

// ===== WITHDRAWAL =====
@Test
    void testWithdrawAccountDoesNotExist() {
        double result = bank.withdraw(888, 70.0);
        System.out.println("Test 1 - Actual result: " + result);
        assertEquals(0.0, result);
    }

    @Test
    void testWithdrawZeroAmount() {
        bank.createAccount(1005, 200.0);

        double result = bank.withdraw(1005, 0.0);
        System.out.println("Test 2 - Actual result: " + result);
        assertEquals(-1.0, result);
    }

    @Test
    void testWithdrawAmountGreaterThanBalance() {
        bank.createAccount(1356, 50.0);

        double result = bank.withdraw(1356, 100.0);
        System.out.println("Test 3 - Actual result: " + result);
        assertEquals(-2.0, result);
    }
    @Test
    void testSuccessfulWithdrawal() {
        bank.createAccount(989, 60.0);

        double result = bank.withdraw(989, 20.0);

        System.out.println("Test 4 - Actual result: " + result);

        assertEquals(40.0, result);
    }

// ===== GET ACCOUNT BALANCE =====
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

    @Test
    public void getAccountBalance_nonExistentAccount_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bank.getAccountBalance(9999));
    }

    // A different number than the one created is still "not found"
    @Test
    public void getAccountBalance_otherAccountNumber_throwsNullPointerException() {
        bank.createAccount(1006, 200.0);
        assertThrows(NullPointerException.class, () -> bank.getAccountBalance(1007));
    }

    // Empty system: nothing has been created at all
    @Test
    public void getAccountBalance_emptySystem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bank.getAccountBalance(0));
    }

    // A rejected createAccount (negative balance) leaves the account non-existent
    @Test
    public void getAccountBalance_accountCreationRejected_throwsNullPointerException() {
        bank.createAccount(1008, -50.0);
        assertThrows(NullPointerException.class, () -> bank.getAccountBalance(1008));
    }
}
