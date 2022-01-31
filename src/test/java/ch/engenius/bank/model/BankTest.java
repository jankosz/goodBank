package ch.engenius.bank.model;

import ch.engenius.bank.exceptions.AccountCreationException;
import ch.engenius.bank.exceptions.InvalidAmountException;
import ch.engenius.bank.exceptions.MissingAccountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    private Bank testBank;

    @BeforeEach
    public void init() {
        this.testBank = new Bank();
    }

    @Test
    public void testCreateAccount() {
        int accountNumber = 1;
        this.testBank.registerAccount(accountNumber, 100);

        Account account = testBank.getAccount(accountNumber);
        Assertions.assertEquals(1, account.getAccountNumber());
        Assertions.assertEquals(100.0, account.getMoney());
    }

    @Test
    public void shouldThrowExceptionWhenAmountIsNegative() {
        InvalidAmountException exception = Assertions.assertThrows(InvalidAmountException.class, () -> {
            this.testBank.registerAccount(1, -100);
        });
        assertEquals("Amount cannot be negative", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenDuplicatedAccountIsRegistered() {
        this.testBank.registerAccount(1, 100);
        AccountCreationException exception = Assertions.assertThrows(AccountCreationException.class, () -> {
            this.testBank.registerAccount(1, 200);
        });
        assertEquals("Account with number: 1 already exists", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAccountDoesNotExist() {
        MissingAccountException exception = Assertions.assertThrows(MissingAccountException.class, () -> {
            this.testBank.getAccount(1);
        });
        assertEquals("Account with number: 1 does not exist", exception.getMessage());
    }
}
