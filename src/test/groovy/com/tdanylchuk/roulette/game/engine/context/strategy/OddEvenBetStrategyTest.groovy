package com.tdanylchuk.roulette.game.engine.context.strategy

import spock.lang.Specification
import spock.lang.Unroll

import static com.tdanylchuk.roulette.model.BetType.ODD_EVEN

class OddEvenBetStrategyTest extends Specification {

    private oddEvenBetStrategy = new OddEvenBetStrategy()

    @Unroll
    def "should hit[#expectedReslt] when number[#number] and bet[#bet]"() {
        when:
        def result = oddEvenBetStrategy.hit(number, bet)

        then:
        expectedReslt == result

        where:
        number | bet    || expectedReslt
        32     | 'ODD'  || false
        31     | 'ODD'  || true
        31     | 'EVEN' || false
        32     | 'EVEN' || true
    }

    def "should return ODD_EVEN type"() {
        when:
        def result = oddEvenBetStrategy.getType()

        then:
        ODD_EVEN == result
    }
}
