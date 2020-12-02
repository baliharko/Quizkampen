package Quiz.ClientSide;

import javafx.stage.Screen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public abstract class Constants {

    public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public static final double WINDOW_WIDTH = SCREEN_WIDTH / 3.3;
    public static final double WINDOW_HEIGHT = SCREEN_HEIGHT / 1.7;

    public static final Properties GAMEPROPERTIES = Constants.getGameProperties();
    public static final int ROUNDS = Constants.getRounds();
    public static final int CATEGORIES = Constants.getCategories();
    public static final int QUESTIONS = Constants.getQuestions();

    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 50001;

    public static final String TITLE = "QUIZ-FAJTEN GRUPP 11";

    public static final String COLOR_TRUE = "radial-gradient(focus-distance 0%, center 50% 50%, radius 200%, #b5f5be, #1ee700);";
    public static final String COLOR_FALSE = "radial-gradient(focus-distance 0%, center 50% 50%, radius 200%, #f8cdcd, #f60c0c);";
    public static final String COLOR_SELECTED = "radial-gradient(focus-distance 0%, center 50% 50%, radius 200%, #ffffff, #f1e57e);";

    public static Properties getGameProperties() {
        Properties properties = new Properties();

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

                properties.load(new FileInputStream("src/main/java/Quiz/ClientSide/GameSetup.properties"));
                properties.setProperty("Categories", "4");
                properties.setProperty("Questions", "2");
                properties.setProperty("Rounds", "2");
                System.out.println("File created.");
            } catch (IOException e) {
                System.out.println("Failed to create file.");
                e.printStackTrace();
            }
        }

        try {
            properties.load(new FileInputStream("src/main/java/Quiz/ClientSide/GameSetup.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static int getRounds() {
        return Integer.parseInt(GAMEPROPERTIES.getProperty("Rounds", "2"));
    }

    private static int getQuestions() {
        return Integer.parseInt(GAMEPROPERTIES.getProperty("Questions", "3"));
    }

    private static int getCategories() {
        return Integer.parseInt(GAMEPROPERTIES.getProperty("Categories", "4"));
    }
}
