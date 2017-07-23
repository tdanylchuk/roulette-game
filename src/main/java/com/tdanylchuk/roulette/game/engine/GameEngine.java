package com.tdanylchuk.roulette.game.engine;

import com.tdanylchuk.roulette.game.engine.context.GameContext;
import com.tdanylchuk.roulette.game.reader.InputReader;
import com.tdanylchuk.roulette.model.PlayerBet;
import com.tdanylchuk.roulette.model.RoundResult;
import com.tdanylchuk.roulette.service.PlayerService;
import com.tdanylchuk.roulette.utils.ConsolePrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class GameEngine {

    private final InputReader inputReader;
    private final ConsolePrinter consolePrinter;
    private final long roundCooldown;
    private final GameContext gameContext;
    private final PlayerService playerService;
    private final AtomicInteger roundCounter;
    private final AtomicBoolean isActive;

    public GameEngine(InputReader inputReader,
                      ConsolePrinter consolePrinter,
                      GameContext gameContext,
                      PlayerService playerService,
                      @Value("${round.cooldownMs}") long roundCooldown) {
        this.inputReader = inputReader;
        this.consolePrinter = consolePrinter;
        this.roundCooldown = roundCooldown;
        this.gameContext = gameContext;
        this.playerService = playerService;
        this.roundCounter = new AtomicInteger(0);
        this.isActive = new AtomicBoolean(true);
    }

    public void startGame() {
        startRoundTimer();
        while (isActive.get()) {
            try {
                PlayerBet playerBet = inputReader.readBet();
                gameContext.bet(playerBet);
                consolePrinter.printThxForBet();
            } catch (Exception ex) {
                consolePrinter.printUserBetException(ex.getMessage());
            }
        }
    }

    public void stopGame() {
        isActive.set(false);
    }

    private void startRoundTimer() {
        startRound();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finishRound();
            }
        }, roundCooldown, roundCooldown);
    }

    private void finishRound() {
        RoundResult roundResult = gameContext.finishRound();
        consolePrinter.printRoundResult(roundCounter.intValue(), roundResult);
        playerService.updateResults(roundResult.getPlayerResults());
        consolePrinter.printPlayers();
        startRound();
    }

    private void startRound() {
        roundCounter.incrementAndGet();
        consolePrinter.printStartRoundMessage(roundCounter.intValue());
    }
}
