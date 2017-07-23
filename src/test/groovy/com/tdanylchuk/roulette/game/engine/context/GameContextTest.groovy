package com.tdanylchuk.roulette.game.engine.context

import com.tdanylchuk.roulette.model.Player
import com.tdanylchuk.roulette.model.PlayerBet
import com.tdanylchuk.roulette.model.PlayerResult
import com.tdanylchuk.roulette.model.RoundResult
import spock.lang.Specification

class GameContextTest extends Specification {

    private static final PLAYER_BET = new PlayerBet(new Player('name'), 'EVEN', 2.0)

    private random = Mock(Random)
    private resultCalculator = Mock(ResultCalculator)
    private gameContext = new GameContext(random, resultCalculator)

    def "should add bet to the active list"() {
        when:
        gameContext.bet(PLAYER_BET)

        then:
        0 * _

        expect:
        gameContext.activeBets.size() == 1
        gameContext.activeBets[0] == PLAYER_BET
    }

    def "should finish round"() {
        given:
        def randomNumber = 4
        def number = randomNumber + 1
        def playerResults = [PlayerResult.builder().build()]

        when:
        gameContext.bet(PLAYER_BET)
        def result = gameContext.finishRound()

        then:
        1 * random.nextInt(36) >> randomNumber
        1 * resultCalculator.calculate(number, _) >> playerResults
        0 * _

        expect:
        gameContext.activeBets.size() == 0
        result == new RoundResult(number, playerResults)
    }
}
