package ch.engenius.bank.services;

import ch.engenius.bank.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultBankTransfer implements BankTransfer {

    private static final Logger LOGGER = LogManager.getLogger(DefaultBankTransfer.class);

    @Override
    public boolean transfer(Account from, Account to, double amount) {
        boolean isTranferSuccessful = true;
        boolean isWithdrawalSuccessful = true;

        isWithdrawalSuccessful = withdraw(from, amount, isWithdrawalSuccessful);

        if (isWithdrawalSuccessful) {
            boolean isDepositSuccessful = deposit(to, amount);
            if (!isDepositSuccessful) {
                isTranferSuccessful = false;
                // try to revert withdrawal
                boolean isRevertSuccessful = deposit(from, amount);
                if (isRevertSuccessful) {
                    LOGGER.error("Reverting withdrawal failed.");
                }
            }
        } else {
            isTranferSuccessful = false;
        }
        return isTranferSuccessful;
    }

    private boolean deposit(Account toAccount, double amount) {
        boolean depositSuccessful = true;
        try {
            toAccount.deposit(amount);
        } catch (Exception e) {
            LOGGER.error(String.format("Deposit to account %s failed", toAccount.getAccountNumber()));
            depositSuccessful = false;
        }
        return depositSuccessful;
    }

    private boolean withdraw(Account from, double amount, boolean withdrawalSuccessful) {
        try {
            from.withdraw(amount);
        } catch (Exception e) {
            LOGGER.error(String.format("Withdrawal from account %s failed", from.getAccountNumber()));
            withdrawalSuccessful = false;
        }
        return withdrawalSuccessful;
    }
}
