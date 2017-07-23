package com.tdanylchuk.roulette.service

import com.tdanylchuk.roulette.model.Player
import com.tdanylchuk.roulette.service.repository.PlayerRepository
import spock.lang.Specification
import spock.lang.Unroll

class DefaultPlayerServiceTest extends Specification {

    private static final PLAYER_NAME = 'player_name'
    private static final PLAYER = new Player(PLAYER_NAME)

    private playerRepository = Mock(PlayerRepository)
    private DefaultPlayerService cachedPlayerStorage

    def setup() {
        1 * playerRepository.findAll() >> [PLAYER]
        cachedPlayerStorage = new DefaultPlayerService(playerRepository)
    }

    def "should return all"() {
        when:
        def result = cachedPlayerStorage.getAll()

        then:
        result.size() == 1
        result[0] == PLAYER
    }

    @Unroll
    def "should return by name[#name] result[#expectedResult]"() {
        when:
        def result = cachedPlayerStorage.getByName(name)

        then:
        result == expectedResult

        where:
        name          || expectedResult
        PLAYER_NAME   || Optional.of(PLAYER)
        'random_name' || Optional.empty()
    }
}
