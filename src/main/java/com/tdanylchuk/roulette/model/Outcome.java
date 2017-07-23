package com.tdanylchuk.roulette.model;

public enum Outcome {
    WIN,
    LOSE;

    public static Outcome valueOf(final boolean hit) {
        return hit ? WIN : LOSE;
    }
}
