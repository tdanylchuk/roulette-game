package com.tdanylchuk.roulette.model;

import lombok.Value;

import java.util.Collection;

@Value
public class RoundResult {
    int number;
    Collection<PlayerResult> playerResults;
}
