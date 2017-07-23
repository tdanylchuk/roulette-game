package com.tdanylchuk.roulette.game.reader

import com.tdanylchuk.roulette.model.Player
import com.tdanylchuk.roulette.model.PlayerBet
import com.tdanylchuk.roulette.service.PlayerService
import spock.lang.Specification
import spock.lang.Unroll

class DefaultInputReaderTest extends Specification {

    private static final PLAYER_NAME = 'Taras'
    private static final PLAYER = new Player(PLAYER_NAME)

    private playerStorage = Mock(PlayerService)
    private reader = Mock(BufferedReader)
    private consoleInputReader = new DefaultInputReader(playerStorage, reader)

    @Unroll
    def "should return player bet from line[#line]"() {
        when:
        def result = consoleInputReader.readBet()

        then:
        1 * reader.readLine() >> line
        1 * playerStorage.getByName(PLAYER_NAME) >> Optional.of(PLAYER)
        0 * _

        expect:
        result == new PlayerBet(PLAYER, bet, 2.0)

        where:
        line           || bet
        'Taras EVEN 2' || 'EVEN'
        'Taras 2 2'    || '2'
    }

    @Unroll
    def "should throw exception if input[#line] invalid"() {
        when:
        consoleInputReader.readBet()

        then:
        1 * reader.readLine() >> line
        _ * playerStorage.getByName(PLAYER_NAME) >> Optional.ofNullable(player)
        0 * _

        and:
        thrown(IllegalArgumentException)

        where:
        line              | player
        'TarasEVEN 2'     | PLAYER
        'Taras ODDD 2'    | PLAYER
        'Taras EVEN -2'   | PLAYER
        'Taras EVEN fdgs' | PLAYER
        'Taras EVEN  \n'  | PLAYER
        'Taras  \n 2'     | PLAYER
        '\n EVEN 2'       | PLAYER
        null              | PLAYER
        'Taras EVEN 2'    | null
    }

    def "should wait any input"() {
        when:
        consoleInputReader.waitAnyInput()

        then:
        1 * reader.readLine() >> ''
        0 * _
    }
}
