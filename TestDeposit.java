import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestDeposit {
    private BankAccountManagementSystem bank;

    @BeforeEach
    void setUp() {
        bank = new BankAccountManagementSystem();
    }

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
    
}
