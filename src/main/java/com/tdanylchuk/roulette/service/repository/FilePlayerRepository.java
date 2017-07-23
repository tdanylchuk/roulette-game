package com.tdanylchuk.roulette.service.repository;

import com.tdanylchuk.roulette.model.Player;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static com.tdanylchuk.roulette.utils.FileUtils.loadClassPathFileContent;
import static com.tdanylchuk.roulette.utils.FileUtils.loadFileContent;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class FilePlayerRepository implements PlayerRepository {

    private static final String PLAYERS_FILE_PATH_PROPERTY_NAME = "players.file.path";
    private static final String PLAYERS_FILE_NAME = "players.csv";
    private static final String SPLITERATOR = ",";

    @Override
    public List<Player> findAll() {
        try (Stream<String> stream = getPlayersStream()) {
            return stream.map(this::toPlayer)
                    .collect(toList());
        }
    }

    private Stream<String> getPlayersStream() {
        String customPropertiesFilePth = System.getProperties().getProperty(PLAYERS_FILE_PATH_PROPERTY_NAME);
        if (isNotBlank(customPropertiesFilePth)) {
            System.out.println(format("Loading custom players from [%s]...", customPropertiesFilePth));
            return loadFileContent(customPropertiesFilePth);
        } else {
            System.out.println("Loading default players...");
            return loadClassPathFileContent(PLAYERS_FILE_NAME);
        }
    }

    private Player toPlayer(String line) {
        String[] values = line.split(SPLITERATOR, -1);
        String playerName = values[0];
        double totalBet = getDouble(values[1]);
        double totalWin = getDouble(values[2]);
        return new Player(playerName, totalBet, totalWin);
    }

    private double getDouble(final String value) {
        return isBlank(value) ? 0 : Double.valueOf(value);
    }

}
