package com.tdanylchuk.roulette.game.engine.context;

import com.tdanylchuk.roulette.model.PlayerBet;
import com.tdanylchuk.roulette.model.PlayerResult;
import com.tdanylchuk.roulette.model.RoundResult;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class GameContext {

    private static final int NUMBER_BOUND = 36;

    private final Random random;
    private final ResultCalculator resultCalculator;
    private Collection<PlayerBet> activeBets;

    public GameContext(final Random random, final ResultCalculator resultCalculator) {
        this.random = random;
        this.resultCalculator = resultCalculator;
        resetActiveBets();
    }

    public void bet(PlayerBet playerBet) {
        activeBets.add(playerBet);
    }

    public RoundResult finishRound() {
        Collection<PlayerBet> playerBets = swapActiveBets();
        int randomNumber = random.nextInt(NUMBER_BOUND) + 1;
        List<PlayerResult> playerResults = resultCalculator.calculate(randomNumber, playerBets);
        return new RoundResult(randomNumber, playerResults);
    }

    private Collection<PlayerBet> swapActiveBets() {
        Collection<PlayerBet> playerBetsToProcess = activeBets;
        resetActiveBets();
        return playerBetsToProcess;
    }

    private void resetActiveBets() {
        activeBets = new ConcurrentLinkedQueue<>();
    }
}
