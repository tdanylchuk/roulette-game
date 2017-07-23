package com.tdanylchuk.roulette.model;

import lombok.Value;

@Value
public class PlayerBet {

    Player player;
    String bet;
    double sum;
}
