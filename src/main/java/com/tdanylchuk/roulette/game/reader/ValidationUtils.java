package com.tdanylchuk.roulette.game.reader;

import com.tdanylchuk.roulette.model.OddEvenType;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import static java.lang.String.format;

@UtilityClass
class ValidationUtils {

    static void validateInputs(final String[] split) {
        if (split.length != 3) {
            throw new IllegalArgumentException("Please, ensure, that bet contains all parts. Format - 'Name Bet Sum'");
        }
    }

    static void validateNonEmptyInput(String input, String subject) {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException(format("Please make sure that [%s] is set", subject));
        }
    }

    static double validateAndGetSum(final String sumString) {
        validateNonEmptyInput(sumString, "sum");
        Double sum = Double.valueOf(sumString);
        if (sum <= 0.0) {
            throw new IllegalArgumentException("Sum of the bet should be > 0.");
        }
        return sum;
    }

    static String validateAndGetBet(final String bet) {
        validateNonEmptyInput(bet, "bet");
        String errorMessage = "Bet is incorrect. Should be in range of 0 < bet < 37 or 'EVEN' or 'ODD'";
        if (NumberUtils.isDigits(bet)) {
            validateNumericBet(bet, errorMessage);
        } else {
            validateOddEvenBet(bet, errorMessage);
        }
        return bet;
    }

    private void validateOddEvenBet(final String bet, final String errorMessage) {
        OddEvenType type = OddEvenType.lookup(bet);
        if (type == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void validateNumericBet(final String bet, final String errorMessage) {
        int number = Integer.parseInt(bet);
        if (number < 0 || number > 37) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
