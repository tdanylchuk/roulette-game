package com.tdanylchuk.roulette.game.engine.context.strategy;

import com.tdanylchuk.roulette.model.BetType;
import com.tdanylchuk.roulette.model.OddEvenType;
import org.springframework.stereotype.Component;

@Component
public class OddEvenBetStrategy implements BetStrategy {

    @Override
    public boolean hit(final int number, final String bet) {
        OddEvenType betType = OddEvenType.lookup(bet);
        OddEvenType numberType = OddEvenType.lookup(number);
        return betType == numberType;
    }

    @Override
    public BetType getType() {
        return BetType.ODD_EVEN;
    }

}
