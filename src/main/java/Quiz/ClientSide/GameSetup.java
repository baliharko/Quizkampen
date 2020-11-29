package Quiz.ClientSide;

import Quiz.ClientSide.controllers.*;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class GameSetup implements Runnable {

    private final Properties gameProperties = new Properties();

    public int categories;
    public int questions;
    public int rounds;

    private String playerName;
    private GameInterface gameInterface;

    private Thread thread;
    private Client client;

    //Controllers
    private EnterNameInterfaceController enterNameInterfaceController;
    private QuestionInterfaceController questionInterfaceController;
    private SelectCategoryInterfaceController selectCategoryInterfaceController;
    private WaitController waitController;
    private ResultFromRoundInterfaceController resultFromRoundInterfaceController;

    public GameSetup(GameInterface gameInterface) {

        this.thread = new Thread(this);
        this.loadPropertiesFromFile();
        this.gameInterface = gameInterface;
        this.enterNameInterfaceController = this.gameInterface.enterNameController;
        this.questionInterfaceController = this.gameInterface.questionController;
        this.selectCategoryInterfaceController = this.gameInterface.selectCategoryController;
        this.waitController = this.gameInterface.waitController;
        this.resultFromRoundInterfaceController = this.gameInterface.resultFromRoundController;
        this.thread.start();
    }

    private void loadPropertiesFromFile() {

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

                gameProperties.load(new FileInputStream("src/main/java/Quiz/ClientSide/GameSetup.properties"));
                gameProperties.setProperty("Categories", "4");
                gameProperties.setProperty("Questions", "2");
                gameProperties.setProperty("Rounds", "2");
                System.out.println("File created.");
            } catch (IOException e) {
                System.out.println("Failed to create file.");
                e.printStackTrace();
            }
        }

        try {
            gameProperties.load(new FileInputStream("src/main/java/Quiz/ClientSide/GameSetup.properties"));
        } catch (IOException e) {
            System.out.println("File could not be found");
        }
        categories = Integer.parseInt(gameProperties.getProperty("Categories", "4"));
        questions = Integer.parseInt(gameProperties.getProperty("Questions", "2"));
        rounds = Integer.parseInt(gameProperties.getProperty("Rounds", "2"));
    }

    public int getCategories() {
        return categories;
    }

    public int getQuestions() {
        return questions;
    }

    public int getRounds() {
        return rounds;
    }

    public EnterNameInterfaceController getEnterNameInterfaceController() {
        return enterNameInterfaceController;
    }

    public QuestionInterfaceController getQuestionInterfaceController() {
        return questionInterfaceController;
    }

    public SelectCategoryInterfaceController getSelectCategoryInterfaceController() {
        return selectCategoryInterfaceController;
    }

    public WaitController getWaitController() {
        return waitController;
    }

    public ResultFromRoundInterfaceController getResultFromRoundInterfaceController(){
        return resultFromRoundInterfaceController;
    }

    @Override
    public void run() {
        this.enterNameInterfaceController.enterNameField.setOnAction(event -> {
            Platform.runLater(() -> {
                this.playerName = this.enterNameInterfaceController.getEnterNameFieldText();
                this.enterNameInterfaceController.enterNameField.setText("");

                if (!this.playerName.isBlank() && this.playerName != null) {
                    // Ger Client tillgång till kontrollern för GUI
                    this.client = new Client(this, this.playerName);
                    this.gameInterface.primaryStage.setScene(this.gameInterface.resultRoundScene);
                }
            });
        });

        for (Button b : selectCategoryInterfaceController.categoryButtons) {
            b.setOnAction(event -> {
                System.out.println(b.getText()); // Skickas till databasen och får tillbaka frågor i vald kategori.
            });
        }
    }
}
