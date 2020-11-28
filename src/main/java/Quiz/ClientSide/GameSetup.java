package Quiz.ClientSide;

import Quiz.ClientSide.controllers.EnterNameInterfaceController;
import Quiz.ClientSide.controllers.QuestionInterfaceController;
import Quiz.ClientSide.controllers.SelectCategoryInterfaceController;
import Quiz.ClientSide.controllers.WaitController;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
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

    public GameSetup(GameInterface gameInterface) {

        this.thread = new Thread(this);
        this.loadPropertiesFromFile();
        this.gameInterface = gameInterface;
        this.enterNameInterfaceController = this.gameInterface.enterNameController;
        this.questionInterfaceController = this.gameInterface.questionController;
        this.selectCategoryInterfaceController = this.gameInterface.selectCategoryController;
        this.waitController = this.gameInterface.waitController;
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

    @Override
    public void run() {

        /* Sätter playername från enterName - fönstret och skapar sedan upp en Client.
            Byter sedan scen i GameInterface. */
        this.enterNameInterfaceController.enterNameField.setOnAction(event -> {
            Platform.runLater(() -> {
                this.playerName = this.enterNameInterfaceController.getEnterNameFieldText();
                this.enterNameInterfaceController.enterNameField.setText("");

                if (!this.playerName.isBlank() && this.playerName != null) {
                    // Ger Client tillgång till kontrollern för GUI
                    this.client = new Client(this, this.playerName);
                    this.gameInterface.primaryStage.setScene(this.gameInterface.questionScene);
                }
            });
        });

        // Skickar texten på markerad knapp i frågerutan till ClientHandler och hanteras av ClientProtocol
        this.getQuestionInterfaceController().acceptButton.setOnAction(event -> {
            if (this.getQuestionInterfaceController().getAcceptButtonText().equalsIgnoreCase("Svara")) {
                Request newRequest = new Request(RequestStatus.ANSWER);
                // Ger texten på knappen till Request - objektet
                newRequest.setAnswerText(Objects.requireNonNull(getQuestionInterfaceController().getSelectedToggleText()));
                // Ger den valda knappens index till Request - objektet
                newRequest.setAnswerButtonIndex(getQuestionInterfaceController().group.getToggles().indexOf(
                        getQuestionInterfaceController().group.getSelectedToggle()));

                try {
                    // Skicka request till ClientHandler för processering av ClientProtocol
                    this.client.getClientOutStream().writeObject(newRequest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("sent " + Objects.requireNonNull(getQuestionInterfaceController().getSelectedToggleText()));
            }
            else if (this.getQuestionInterfaceController().getAcceptButtonText().equalsIgnoreCase("Nästa fråga")) {
                try {
                    this.client.getClientOutStream().writeObject(new Request(RequestStatus.NEXT_QUESTION));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        for (Button b : selectCategoryInterfaceController.categoryButtons) {
            b.setOnAction(event -> {
                System.out.println(b.getText()); // Skickas till databasen och får tillbaka frågor i vald kategori.
            });
        }
    }
}

// TODO - Knapparna måste refreshas när nästa fråga laddas in (ta bort fokus)
// TODO - Få in kategorierna och x antal frågor per rond
// TODO - Poäng
// TODO - efter x antal frågor måste spelare x vänta på spelare y