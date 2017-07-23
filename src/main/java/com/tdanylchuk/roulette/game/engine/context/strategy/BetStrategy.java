package com.tdanylchuk.roulette.game.engine.context.strategy;

import com.tdanylchuk.roulette.model.BetType;
import com.tdanylchuk.roulette.model.Outcome;

public interface BetStrategy {

    default Outcome getOutcome(int number, String bet) {
        return Outcome.valueOf(hit(number, bet));
    }

    boolean hit(int number, String bet);

    BetType getType();

}
