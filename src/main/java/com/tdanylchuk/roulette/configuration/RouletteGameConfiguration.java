package com.tdanylchuk.roulette.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@ComponentScan(basePackages = "com.tdanylchuk.roulette")
@PropertySource("classpath:application.properties")
public class RouletteGameConfiguration {

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public BufferedReader consoleReader() throws UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
    }

}
