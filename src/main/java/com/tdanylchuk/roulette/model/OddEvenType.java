package com.tdanylchuk.roulette.model;

public enum OddEvenType {
    ODD,
    EVEN;

    public static OddEvenType lookup(int number) {
        return number % 2 == 0 ? EVEN : ODD;
    }

    public static OddEvenType lookup(String bet) {
        for (OddEvenType type : OddEvenType.values()) {
            if (type.name().equalsIgnoreCase(bet)) {
                return type;
            }
        }
        return null;
    }
}
