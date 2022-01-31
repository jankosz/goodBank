package ch.engenius.bank.exceptions;

public class MissingAccountException extends RuntimeException {

    public MissingAccountException(String message) {
        super(message);
    }
}
