package ch.engenius.bank.exceptions;

public class AccountCreationException extends RuntimeException {

    public AccountCreationException(String message) {
        super(message);
    }
}
