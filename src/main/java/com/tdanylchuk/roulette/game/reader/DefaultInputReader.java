package com.tdanylchuk.roulette.game.reader;

import com.tdanylchuk.roulette.service.PlayerService;
import com.tdanylchuk.roulette.model.OddEvenType;
import com.tdanylchuk.roulette.model.Player;
import com.tdanylchuk.roulette.model.PlayerBet;
import lombok.SneakyThrows;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;

import static com.tdanylchuk.roulette.game.reader.ValidationUtils.validateInputs;
import static com.tdanylchuk.roulette.game.reader.ValidationUtils.validateNonEmptyInput;
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
        if(playerInput == null) {
            throw new IllegalArgumentException("Null receved instead of user input.");
        }
        String[] split = playerInput.split(PLAYER_BET_SPLITERATOR);
        validateInputs(split);
        Player player = getPlayer(split[0]);
        String bet = getBet(split[1]);
        double sum = getSum(split[2]);
        return new PlayerBet(player, bet, sum);
    }

    private double getSum(final String sumString) {
        validateNonEmptyInput(sumString, "sum");
        Double sum = Double.valueOf(sumString);
        if (sum <= 0.0) {
            throw new IllegalArgumentException("Sum of the bet should be > 0.");
        }
        return sum;
    }

    private String getBet(final String bet) {
        validateNonEmptyInput(bet, "bet");
        String errorMessage = "Bet is incorrect. Should be in range of 0 < bet < 37 or 'EVEN' or 'ODD'";
        if (NumberUtils.isDigits(bet)) {
            int number = Integer.parseInt(bet);
            if (number < 0 || number > 37) {
                throw new IllegalArgumentException(errorMessage);
            }
        } else {
            OddEvenType type = OddEvenType.lookup(bet);
            if (type == null) {
                throw new IllegalArgumentException(errorMessage);
            }
        }
        return bet;
    }

    private Player getPlayer(final String playerName) {
        validateNonEmptyInput(playerName, "name");
        return playerService.getByName(playerName).orElseThrow(() ->
                new IllegalArgumentException(format("Player[%s] is undefined.", playerName)));
    }

}
