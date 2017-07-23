package com.tdanylchuk.roulette.service;

import com.tdanylchuk.roulette.model.Player;
import com.tdanylchuk.roulette.model.PlayerResult;

import java.util.Collection;
import java.util.Optional;


public interface PlayerService {

    Collection<Player> getAll();

    Optional<Player> getByName(String name);

    void updateResults(Collection<PlayerResult> playerResults);
}
