package Quiz.ClientSide;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameSetup {

    private static final Properties gameSetup = new Properties();

    public static int categories;
    public static int questions;
    public static int rounds;


    public GameSetup() {
        try {
            gameSetup.load(new FileInputStream("src/main/java/Quiz/ClientSide/GameSetup.properties"));
        } catch (IOException e) {
            System.out.println("File could not be found");
        }
        categories = Integer.parseInt(gameSetup.getProperty("Categories", "4"));
        questions = Integer.parseInt(gameSetup.getProperty("Questions", "2"));
        rounds = Integer.parseInt(gameSetup.getProperty("Rounds", "2"));
    }
}
