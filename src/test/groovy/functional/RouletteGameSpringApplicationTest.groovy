package functional

import com.tdanylchuk.roulette.configuration.RouletteGameConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = RouletteGameConfiguration)
class RouletteGameSpringApplicationTest extends Specification {

    @Autowired
    private ApplicationContext applicationContext

    def "should run spring context"() {
        expect:
        applicationContext != null
    }
}
