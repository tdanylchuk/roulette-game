package functional

import com.tdanylchuk.roulette.configuration.RouletteGameConfiguration
import com.tdanylchuk.roulette.game.RouletteGameStarter
import com.tdanylchuk.roulette.game.engine.GameEngine
import com.tdanylchuk.roulette.model.Player
import com.tdanylchuk.roulette.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.mock.DetachedMockFactory
import spock.util.concurrent.PollingConditions

@ContextConfiguration(classes = [RouletteGameConfiguration, TestConfiguration])
class RouletteGameFunctionalTest extends Specification {

    private final static POLLING_CONDITION = new PollingConditions(timeout: 60, delay: 1)
    private final static OUT_STREAM = new PipedOutputStream()

    @Autowired
    private ApplicationContext applicationContext
    @Autowired
    private RouletteGameStarter rouletteGameStarter
    @Autowired
    private GameEngine gameEngine
    @Autowired
    private PlayerService playerService

    @Autowired
    private Random random

    def cleanup() {
        gameEngine.stopGame()
        OUT_STREAM.close()
    }

    def "should calculate all bets"() {
        given:
        def inputs = ['', '',
                      'Barbaraclea 2 4.0',
                      'Barbaraclea 10 3',
                      'Barbaraclea 21 2',
                      'Tiki_Monkey EVEN 6',
                      'Tiki_Monkey 1 87',
                      'Barbaraclea EVEN 15',
                      'Tiki_Monkey ODD 87',
                      'Tiki_Monkey ODD 2.0',
                      'Barbaraclea ODD 21']
        def testWriter = new BufferedWriter(new OutputStreamWriter(OUT_STREAM))
        def player1 = new Player('Barbaraclea', 45.0, 114.0)
        def player2 = new Player('Tiki_Monkey', 182.0, 178.0)
        random.nextInt(36) >> 20

        when: 'start game on background'
        Thread.start { rouletteGameStarter.start() }

        then: 'mock user input'
        inputs.forEach {
            testWriter.writeLine(it)
            testWriter.flush()
        }

        and: 'wait round completed'
        POLLING_CONDITION.eventually { assert gameEngine.roundCounter.intValue() == 2 }

        expect: 'check players data'
        playerService.getAll() as ArrayList == [player1, player2]
    }

    @Configuration
    static class TestConfiguration {
        private factory = new DetachedMockFactory()

        @Bean
        Random random() {
            factory.Mock(Random)
        }

        @Bean
        BufferedReader consoleReader() {
            new BufferedReader(new InputStreamReader(new PipedInputStream(OUT_STREAM)))
        }
    }

}
