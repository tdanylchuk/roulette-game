package com.tdanylchuk.roulette.service.repository

import com.tdanylchuk.roulette.model.Player
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

class FilePlayerRepositoryTest extends Specification {

    private filePlayerRepository = new FilePlayerRepository()

    def cleanup() {
        System.clearProperty('players.file.path')
    }

    def "should find default players"() {
        given:
        def player1 = new Player("Tiki_Monkey", 0, 0)
        def player2 = new Player("Barbaraclea", 0, 0)

        when:
        def result = filePlayerRepository.findAll()

        then:
        result == [player1, player2]
    }

    def "should find custom players"() {
        given:
        def player1 = new Player("Player1", 0.2, 3.0)
        def player2 = new Player("Player2", 0, 43.0)
        def path = new ClassPathResource('test-players.csv').getFile().getPath()
        System.setProperty('players.file.path', path)

        when:
        def result = filePlayerRepository.findAll()

        then:
        result == [player1, player2]
    }
}
