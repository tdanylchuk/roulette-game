package com.tdanylchuk.roulette.game.engine.context

import com.tdanylchuk.roulette.game.engine.context.strategy.BetStrategy
import com.tdanylchuk.roulette.model.Player
import com.tdanylchuk.roulette.model.PlayerBet
import com.tdanylchuk.roulette.model.PlayerResult
import spock.lang.Specification
import spock.lang.Unroll

import static com.tdanylchuk.roulette.model.BetType.ODD_EVEN
import static com.tdanylchuk.roulette.model.Outcome.LOSE
import static com.tdanylchuk.roulette.model.Outcome.WIN

class ResultCalculatorTest extends Specification {

    private betStrategy = Mock(BetStrategy)
    private ResultCalculator resultCalculator

    def setup() {
        1 * betStrategy.getType() >> ODD_EVEN
        resultCalculator = new ResultCalculator([betStrategy])
    }

    @Unroll
    def "should calculate results for bet[#bet] outcome[#outcome]"() {
        given:
        def number = 4
        def player = new Player('name')
        def bettingSum = 2.0
        def playerBet = new PlayerBet(player, bet, bettingSum)
        def playerResult = new PlayerResult(player, bet, outcome, bettingSum, winnings)

        when:
        def results = resultCalculator.calculate(number, [playerBet])

        then:
        1 * betStrategy.getOutcome(number, bet) >> outcome
        0 * _

        expect:
        results == [playerResult]

        where:
        bet    | outcome || winnings
        'EVEN' | WIN     || 4.0
        'odD'  | LOSE    || 0.0
    }

    def "should fail if no strategy found"() {
        given:
        def number = 4
        def playerBet = new PlayerBet(new Player('name'), '12', 2.0)

        when:
        resultCalculator.calculate(number, [playerBet])

        then:
        0 * _

        and:
        thrown(IllegalArgumentException)
    }
}
