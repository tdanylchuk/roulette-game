package com.tdanylchuk.roulette.game.reader;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import static java.lang.String.format;

@UtilityClass
public class ValidationUtils {

    public static void validateInputs(final String[] split) {
        if (split.length != 3) {
            throw new IllegalArgumentException("Please, ensure, that bet contains all parts. Format - 'Name Bet Sum'");
        }
    }

    public static void validateNonEmptyInput(String input, String subject) {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException(format("Please make sure that [%s] is set", subject));
        }
    }
}
