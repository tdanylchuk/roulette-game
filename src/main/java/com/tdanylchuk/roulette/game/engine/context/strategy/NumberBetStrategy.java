package com.tdanylchuk.roulette.game.engine.context.strategy;

import com.tdanylchuk.roulette.model.BetType;
import org.springframework.stereotype.Component;

import static com.tdanylchuk.roulette.model.BetType.NUMBER;

@Component
public class NumberBetStrategy implements BetStrategy {

    @Override
    public boolean hit(final int number, final String bet) {
        return number == Integer.parseInt(bet);
    }

    @Override
    public BetType getType() {
        return NUMBER;
    }
}
