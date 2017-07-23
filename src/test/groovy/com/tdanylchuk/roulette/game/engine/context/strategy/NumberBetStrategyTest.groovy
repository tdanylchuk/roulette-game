package com.tdanylchuk.roulette.game.engine.context.strategy

import spock.lang.Specification
import spock.lang.Unroll

import static com.tdanylchuk.roulette.model.BetType.NUMBER
import static com.tdanylchuk.roulette.model.Outcome.LOSE
import static com.tdanylchuk.roulette.model.Outcome.WIN

class NumberBetStrategyTest extends Specification {

    private numberBetStrategy = new NumberBetStrategy()

    @Unroll
    def "should hit[#expectedReslt] when number[#number] and bet[#bet]"() {
        when:
        def result = numberBetStrategy.hit(number, bet)
        def outcome = numberBetStrategy.getOutcome(number, bet)

        then:
        expectedReslt == result
        expectedOutcome == outcome

        where:
        number | bet  || expectedReslt | expectedOutcome
        31     | '32' || false         | LOSE
        32     | '32' || true          | WIN

    }

    def "should return NUMBER type"() {
        when:
        def result = numberBetStrategy.getType()

        then:
        NUMBER == result
    }
}
