package com.tdanylchuk.roulette.game.engine.context;

import com.tdanylchuk.roulette.game.engine.context.strategy.BetStrategy;
import com.tdanylchuk.roulette.model.BetType;
import com.tdanylchuk.roulette.model.Outcome;
import com.tdanylchuk.roulette.model.PlayerBet;
import com.tdanylchuk.roulette.model.PlayerResult;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.tdanylchuk.roulette.model.Outcome.WIN;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Component
public class ResultCalculator {

    private static final double LOSING_MULTIPLIER = 0;

    private final Map<BetType, BetStrategy> betStrategyMap;

    public ResultCalculator(List<BetStrategy> betStrategies) {
        this.betStrategyMap = betStrategies.stream()
                .collect(toMap(BetStrategy::getType, identity()));
    }

    public List<PlayerResult> calculate(int number, Collection<PlayerBet> playerBets) {
        return playerBets.stream()
                .map(bet -> calculateBet(number, bet))
                .collect(toList());
    }

    private PlayerResult calculateBet(int number, PlayerBet playerBet) {
        String bet = playerBet.getBet();
        BetType betType = BetType.lookup(bet);
        Outcome outcome = getBetStrategy(betType).getOutcome(number, bet);
        double bettingSum = playerBet.getSum();
        double winnings = calculateWinnings(outcome, betType, bettingSum);
        return PlayerResult.builder()
                .player(playerBet.getPlayer())
                .bet(bet)
                .winnings(winnings)
                .outcome(outcome)
                .bettingSum(bettingSum)
                .build();
    }

    private double calculateWinnings(final Outcome outcome, final BetType betType, final double sum) {
        double multiplier = outcome == WIN ? betType.getMultiplier() : LOSING_MULTIPLIER;
        return multiplier * sum;
    }

    private BetStrategy getBetStrategy(BetType betType) {
        return ofNullable(betStrategyMap.get(betType)).orElseThrow(() ->
                new IllegalArgumentException(format("Type[%s] is not supported.", betType)));
    }

}
