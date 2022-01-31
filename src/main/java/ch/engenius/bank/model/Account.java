package ch.engenius.bank.model;

import ch.engenius.bank.exceptions.InvalidAmountException;

import java.math.BigDecimal;

public class Account {
    private int accountNumber;
    private BigDecimal money;

    public Account(int number, double money) {
        this.accountNumber = number;
        this.setMoney(money);
    }

    public synchronized void withdraw(double amount) {
        BigDecimal withdrawAmount = BigDecimal.valueOf(amount);
        BigDecimal result = getMoneyAsBigDecimal().subtract(withdrawAmount);
        if (BigDecimal.ZERO.compareTo(result) > 0) {
            throw new InvalidAmountException("Not enough credits on account: " + result.doubleValue());
        }
        setMoney(result);
    }

    public synchronized void deposit(double amount) {
        BigDecimal depositAmount = BigDecimal.valueOf(amount);
        BigDecimal result = getMoneyAsBigDecimal().add(depositAmount);
        setMoney(result);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getMoney() {
        return money.doubleValue();
    }

    private synchronized void setMoney(double money) {
        setMoney(BigDecimal.valueOf(money));
    }

    private synchronized void setMoney(BigDecimal money) {
        if (BigDecimal.ZERO.compareTo(money) > 0) {
            throw new InvalidAmountException("Amount cannot be negative");
        }
        this.money = money;
    }

    public BigDecimal getMoneyAsBigDecimal() {
        return money;
    }

}
