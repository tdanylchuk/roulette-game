package com.tdanylchuk.roulette.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlayerResult {
    Player player;
    String bet;
    Outcome outcome;
    double bettingSum;
    double winnings;
}
