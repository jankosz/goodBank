package ch.engenius.bank.services;

import ch.engenius.bank.model.Account;

public interface BankTransfer {

    boolean transfer(Account from, Account to, double amount);
}
