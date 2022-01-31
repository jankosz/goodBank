package ch.engenius.bank.services;

import ch.engenius.bank.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultBankTransferTest {

    private DefaultBankTransfer testObject;

    @BeforeEach
    public void init() {
        this.testObject = new DefaultBankTransfer();
    }

    @Test
    public void testSuccessfulTransfer() {
        Account fromAccount = new Account(1, 1000);
        Account toAccount = new Account(2, 500);

        boolean transferStatus = this.testObject.transfer(fromAccount, toAccount, 100);

        Assertions.assertTrue(transferStatus);
        Assertions.assertEquals(900.0, fromAccount.getMoney());
        Assertions.assertEquals(600.0, toAccount.getMoney());
    }

    @Test
    public void testFailedTransfer() {
        Account fromAccount = new Account(1, 1000);
        Account toAccount = new Account(2, 500);

        boolean transferStatus = this.testObject.transfer(fromAccount, toAccount, 2000);

        Assertions.assertFalse(transferStatus);
        Assertions.assertEquals(1000.0, fromAccount.getMoney());
        Assertions.assertEquals(500.0, toAccount.getMoney());
    }
}
