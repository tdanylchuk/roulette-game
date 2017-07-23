package com.tdanylchuk.roulette.game.reader;

import com.tdanylchuk.roulette.model.Player;
import com.tdanylchuk.roulette.model.PlayerBet;
import com.tdanylchuk.roulette.service.PlayerService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;

import static com.tdanylchuk.roulette.game.reader.ValidationUtils.*;
import static java.lang.String.format;

@Component
public class DefaultInputReader implements InputReader {

    private final static String PLAYER_BET_SPLITERATOR = " ";

    private final PlayerService playerService;
    private final BufferedReader reader;

    public DefaultInputReader(final PlayerService playerService, final BufferedReader reader) {
        this.playerService = playerService;
        this.reader = reader;
    }

    @SneakyThrows
    public PlayerBet readBet() {
        String playerInput = reader.readLine();
        return convertToBet(playerInput);
    }

    @SneakyThrows
    public void waitAnyInput() {
        reader.readLine();
    }

    private PlayerBet convertToBet(final String playerInput) {
        if (playerInput == null) {
            throw new IllegalArgumentException("Null receved instead of user input.");
        }
        String[] split = playerInput.split(PLAYER_BET_SPLITERATOR);
        validateInputs(split);
        Player player = getPlayer(split[0]);
        String bet = validateAndGetBet(split[1]);
        double sum = validateAndGetSum(split[2]);
        return new PlayerBet(player, bet, sum);
    }

    private Player getPlayer(final String playerName) {
        validateNonEmptyInput(playerName, "name");
        return playerService.getByName(playerName).orElseThrow(() ->
                new IllegalArgumentException(format("Player[%s] is undefined.", playerName)));
    }

}
