package com.tdanylchuk.roulette.service;

import com.tdanylchuk.roulette.model.Player;
import com.tdanylchuk.roulette.model.PlayerResult;
import com.tdanylchuk.roulette.service.repository.PlayerRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class DefaultPlayerService implements PlayerService {

    private final Map<String, Player> playersMap;

    public DefaultPlayerService(PlayerRepository playerRepository) {
        this.playersMap = playerRepository.findAll().stream()
                .collect(toMap(Player::getName, identity()));
    }

    @Override
    public Collection<Player> getAll() {
        return playersMap.values();
    }

    @Override
    public Optional<Player> getByName(String name) {
        return ofNullable(playersMap.get(name));
    }

    @Override
    public void updateResults(final Collection<PlayerResult> playerResults) {
        playerResults.forEach(this::updatePlayer);
    }

    private void updatePlayer(PlayerResult playerResult) {
        Player player = playersMap.get(playerResult.getPlayer().getName());
        player.setTotalBet(player.getTotalBet() + playerResult.getBettingSum());
        player.setTotalWin(player.getTotalWin() + playerResult.getWinnings());
    }
}
