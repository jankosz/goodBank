package ch.engenius.bank.services;

import ch.engenius.bank.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultBankTransfer implements BankTransfer {

    private static final Logger LOGGER = LogManager.getLogger(DefaultBankTransfer.class);

    @Override
    public boolean transfer(Account from, Account to, double amount) {
        boolean tranferSuccessful = true;
        boolean withdrawalSuccessful = true;
        try {
            from.withdraw(amount);
        } catch (Exception e) {
            LOGGER.error(String.format("Withdrawal from account %s failed", from.getAccountNumber()));
            withdrawalSuccessful = false;
            tranferSuccessful = false;
        }

        if (withdrawalSuccessful) {
            try {
                to.deposit(amount);
            } catch (Exception e) {
                LOGGER.error(String.format("Deposit to account %s failed", from.getAccountNumber()));
                tranferSuccessful = false;
                // try to revert withdrawal
                try {
                    from.deposit(amount);
                } catch (Exception ex) {
                    LOGGER.error("Reverting withdrawal failed.");
                }
            }
        }
        return tranferSuccessful;
    }
}
