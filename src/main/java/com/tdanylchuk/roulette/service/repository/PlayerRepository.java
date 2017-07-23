package com.tdanylchuk.roulette.service.repository;

import com.tdanylchuk.roulette.model.Player;

import java.util.Collection;

public interface PlayerRepository {

    Collection<Player> findAll();
}
