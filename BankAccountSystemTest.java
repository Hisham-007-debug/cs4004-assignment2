import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BankAccountSystemTest {

    @Test
    public void testCreateAccount() {

        BankAccountManagementSystem bank = new BankAccountManagementSystem();

// Test 1: Create an account with a positive balance
        assertTrue(bank.createAccount(12345, 1.0));

// Test 2: Attempt to create a duplicate account
        assertFalse(bank.createAccount(12345, 0.0));

// Test 3: Attempt to create an account with a negative balance
        assertFalse(bank.createAccount(234567, -1.0));

// Test 4: Create an account with a zero balance
        assertTrue(bank.createAccount(234567, 0.0));
    }
}

