# Roulette game
##### To run the game please follow next steps:

- build it first with `mvn clean verify`
- change directory to target `cd target`
- run the game
- follow instructions inside game

###### Two ways to run:
- `java -jar roulette-game-jar-with-dependencies.jar` (Sorry for standalone, maybe for someone it would be easier)
- `mvn exec:java  -Dexec.mainClass="com.tdanylchuk.roulette.RouletteGameApplication"`

##### There is an option to specify output file instead of using default one.
It could be reached via setting `players.file.path` property.

Example : `java -jar -Dplayers.file.path={pathToFile} roulette-game-jar-with-dependencies.jar`

Structure of the file should be coma-separated values:
- Player name
- Starting bet
- Starting win

Example of the single record: `Taras,2.0,3`
 
Note: empty value would be treated as `0`.

### ENJOY and GOOD LUCK! :D

