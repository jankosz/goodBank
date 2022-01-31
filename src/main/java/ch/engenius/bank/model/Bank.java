package ch.engenius.bank.model;

import ch.engenius.bank.exceptions.AccountCreationException;
import ch.engenius.bank.exceptions.MissingAccountException;

import java.util.HashMap;

public class Bank {
    private HashMap<Integer, Account> accounts = new HashMap<>();

    public Account registerAccount(int accountNumber, int amount) {
        Account account = new Account(accountNumber, amount);
        if (this.accounts.containsKey(accountNumber)) {
            throw new AccountCreationException(String.format("Account with number: %s already exists", accountNumber));
        }
        this.accounts.put(accountNumber, account);
        return account;
    }

    public Account getAccount(int number) {
        if (!this.accounts.containsKey(number)) {
            throw new MissingAccountException(String.format("Account with number: %s does not exist", number));
        }
        return this.accounts.get(number);
    }
}
