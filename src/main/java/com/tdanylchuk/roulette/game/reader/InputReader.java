package com.tdanylchuk.roulette.game.reader;

import com.tdanylchuk.roulette.model.PlayerBet;

public interface InputReader {

    PlayerBet readBet();

    void waitAnyInput();
}
