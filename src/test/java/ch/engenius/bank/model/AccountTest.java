package ch.engenius.bank.model;

import ch.engenius.bank.exceptions.InvalidAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AccountTest {

    private Account testAccount;

    @ParameterizedTest
    @MethodSource("depositParametersProvider")
    public void testDeposit(double initAmount, double depositAmount, double expectedAmount) {
        this.testAccount = new Account(1, initAmount);
        this.testAccount.deposit(depositAmount);

        assertEquals(expectedAmount, this.testAccount.getMoneyAsBigDecimal().doubleValue());
    }

    @ParameterizedTest
    @MethodSource("withdrawParametersProvider")
    public void testWithdaw(double initAmount, double withdrawalAmount, double expectedAmount) {
        this.testAccount = new Account(1, initAmount);
        this.testAccount.withdraw(withdrawalAmount);

        assertEquals(expectedAmount, this.testAccount.getMoneyAsBigDecimal().doubleValue());
    }

    @Test
    public void shouldThrowExceptionWhenAmountAfterWithdrawalIsNegative() {
        this.testAccount = new Account(1, 1000);
        InvalidAmountException exception = Assertions.assertThrows(InvalidAmountException.class, () -> {
            this.testAccount.withdraw(2000);
        });
        assertEquals("Not enough credits on account: -1000.0", exception.getMessage());
    }

    private static Stream<Arguments> depositParametersProvider() {
        return Stream.of(
                arguments(0, 0.1, 0.1),
                arguments(100, 5, 105),
                arguments(5.1, 0.1, 5.2),
                arguments(929.5112034881753623, 23.09838629475045, 952.6095897829258123)
        );
    }

    private static Stream<Arguments> withdrawParametersProvider() {
        return Stream.of(
                arguments(10, 0.1, 9.9),
                arguments(100, 5, 95),
                arguments(5.1, 0.1, 5.0),
                arguments(929.5112034881753623, 23.09838629475045, 906.412817193425)
        );
    }
}
