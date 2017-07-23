package com.tdanylchuk.roulette;

import com.tdanylchuk.roulette.game.RouletteGameStarter;
import com.tdanylchuk.roulette.configuration.RouletteGameConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class RouletteGameApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RouletteGameConfiguration.class);
        RouletteGameStarter rouletteGameStarter = applicationContext.getBean(RouletteGameStarter.class);
        rouletteGameStarter.start();
    }

}
