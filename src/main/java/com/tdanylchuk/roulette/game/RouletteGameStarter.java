package com.tdanylchuk.roulette.game;

import com.tdanylchuk.roulette.game.engine.GameEngine;
import com.tdanylchuk.roulette.game.reader.InputReader;
import com.tdanylchuk.roulette.utils.ConsolePrinter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static java.lang.String.format;


@Component
@AllArgsConstructor
public class RouletteGameStarter {

    private final InputReader inputReader;
    private final ConsolePrinter consolePrinter;
    private final GameEngine gameEngine;

    public void start() {
        consolePrinter.printBanner();
        consolePrinter.printRules();
        waitPlayerToContinue("load players");
        consolePrinter.printPlayers();
        waitPlayerToContinue("start the game");
        gameEngine.startGame();
    }

    private void waitPlayerToContinue(String action) {
        System.out.println(format("Enter any key to %s...", action));
        inputReader.waitAnyInput();
    }

}
