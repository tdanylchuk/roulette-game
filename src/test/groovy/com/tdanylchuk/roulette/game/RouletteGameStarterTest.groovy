package com.tdanylchuk.roulette.game

import com.tdanylchuk.roulette.game.engine.GameEngine
import com.tdanylchuk.roulette.game.reader.InputReader
import com.tdanylchuk.roulette.utils.ConsolePrinter
import spock.lang.Specification

class RouletteGameStarterTest extends Specification {

    private inputReader = Mock(InputReader)
    private consolePrinter = Mock(ConsolePrinter)
    private gameEngine = Mock(GameEngine)

    private rouletteGameStarter = new RouletteGameStarter(inputReader, consolePrinter, gameEngine)

    def "should start"() {
        when:
        rouletteGameStarter.start()

        then:
        1 * consolePrinter.printBanner()
        1 * consolePrinter.printRules()
        2 * inputReader.waitAnyInput()
        1 * consolePrinter.printPlayers()
        1 * gameEngine.startGame()
        0 * _
    }
}
