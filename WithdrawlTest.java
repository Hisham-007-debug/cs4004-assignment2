import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WithdrawlTest {
    private BankAccountManagementSystem bank;

    @BeforeEach
    void setUp(){
        bank = new BankAccountManagementSystem();
    }

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
}