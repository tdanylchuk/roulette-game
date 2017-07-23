package com.tdanylchuk.roulette.utils;

import com.tdanylchuk.roulette.model.Player;
import com.tdanylchuk.roulette.model.RoundResult;
import com.tdanylchuk.roulette.service.PlayerService;
import de.vandermeer.asciitable.AT_Row;
import de.vandermeer.asciitable.AsciiTable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Stream;

import static com.tdanylchuk.roulette.utils.FileUtils.loadClassPathFileContent;
import static de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment.CENTER;
import static java.lang.String.format;

@Component
@AllArgsConstructor
public class ConsolePrinter {

    private static final String BANNER_FILE_NAME = "banner.txt";

    private final PlayerService playerService;

    public void printBanner() {
        try (Stream<String> stream = loadClassPathFileContent(BANNER_FILE_NAME)) {
            stream.forEach(System.out::println);
        }
    }

    public void printPlayers() {
        Collection<Player> players = playerService.getAll();
        printTable("Players:",
                new String[]{"Name", "Total Win ", "Total Bet"},
                players.stream().map(player -> new String[]{
                        player.getName(),
                        String.valueOf(player.getTotalWin()),
                        String.valueOf(player.getTotalBet())}));
    }

    public void printRules() {
        System.out.println();
        printHeavyHeader("RULES");
        printSingleColumnTable("Each line should contain space separated inputs:", Stream.of(
                "1. The player's name",
                "2. What you want to bet on (either a number from 1-36, EVEN or ODD)",
                "3. How much you want to bet"));
        printSingleColumnTable("Examples:", Stream.of(
                "Tiki_Monkey 2 1.0",
                "Barbara EVEN 3.0"));
    }

    public void printRoundResult(final int roundNumber, final RoundResult roundResult) {
        System.out.println();
        printTable(format("End of the round #[%d]. Winning number [%d]. Results:", roundNumber, roundResult.getNumber()),
                new String[]{"Player", "Bet", "Outcome", "Winnings"},
                roundResult.getPlayerResults().stream()
                        .map(result -> new String[]{
                                result.getPlayer().getName(),
                                result.getBet(),
                                result.getOutcome().name(),
                                String.valueOf(result.getWinnings())}));
    }

    public void printUserBetException(final String message) {
        System.out.println(format("Bet is not processed. Reason - [%s]", message));
    }

    public void printThxForBet() {
        System.out.println("Thank you for the bet! Good luck!");
    }

    public void printStartRoundMessage(final int roundNumber) {
        System.out.println();
        System.out.println();
        printHeavyHeader(format("Round #[%d] has started.", roundNumber));
    }

    private static void printHeavyHeader(final String header) {
        AsciiTable at = new AsciiTable();
        AT_Row row = at.addRow(header);
        row.setPaddingTopChar('v');
        row.setPaddingBottomChar('^');
        row.setTextAlignment(CENTER);
        row.setPadding(1);
        System.out.println(at.render());
    }

    private void printSingleColumnTable(final String header, final Stream<String> stringStream) {
        printTable(header, stringStream.map(string -> new String[]{string}));
    }

    private void printTable(String title, Stream<String[]> records) {
        printTable(title, null, records);
    }

    private void printTable(String title, String[] header, Stream<String[]> records) {
        System.out.println(title);
        AsciiTable at = new AsciiTable();
        if (header != null) {
            at.addRule();
            at.addRow(header);
        }
        at.addRule();
        records.forEach(at::addRow);
        at.addRule();
        System.out.println(at.render());
        System.out.println();
    }

}
