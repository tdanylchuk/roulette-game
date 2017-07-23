package com.tdanylchuk.roulette.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Getter
@AllArgsConstructor
public enum BetType {
    NUMBER(36.0),
    ODD_EVEN(2.0);

    private double multiplier;

    public static BetType lookup(final String bet) {
        return isNumeric(bet) ? NUMBER : ODD_EVEN;
    }

}
