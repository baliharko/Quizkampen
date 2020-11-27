package Quiz.ClientSide;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class GameSetup {

    private static final Properties gameSetup = new Properties();

    public static int categories;
    public static int questions;
    public static int rounds;

    public GameSetup() {

        if (!new File("src/main/java/Quiz/ClientSide/GameSetup.properties").exists()) {
            try {
                Files.createFile(Path.of("src/main/java/Quiz/ClientSide/GameSetup.properties"));

                try (FileWriter fw = new FileWriter("src/main/java/Quiz/ClientSide/GameSetup.properties")) {
                    fw.append("Categories=4\n");
                    fw.append("Questions=2\n");
                    fw.append("Rounds=2");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                gameSetup.load(new FileInputStream("src/main/java/Quiz/ClientSide/GameSetup.properties"));
                gameSetup.setProperty("Categories", "4");
                gameSetup.setProperty("Questions", "2");
                gameSetup.setProperty("Rounds", "2");
                System.out.println("File created.");
            } catch (IOException e) {
                System.out.println("Failed to create file.");
                e.printStackTrace();
            }
        }

        try {
            gameSetup.load(new FileInputStream("src/main/java/Quiz/ClientSide/GameSetup.properties"));
        } catch (IOException e) {
            System.out.println("File could not be found");
        }
        categories = Integer.parseInt(gameSetup.getProperty("Categories", "4"));
        questions = Integer.parseInt(gameSetup.getProperty("Questions", "2"));
        rounds = Integer.parseInt(gameSetup.getProperty("Rounds", "2"));
    }

    public static int getCategories() {
        return categories;
    }

    public static int getQuestions() {
        return questions;
    }

    public static int getRounds() {
        return rounds;
    }
}
