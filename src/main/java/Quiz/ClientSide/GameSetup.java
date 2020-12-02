package Quiz.ClientSide;

import Quiz.ClientSide.controllers.*;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class GameSetup implements Runnable {

    private String playerName;
    private GameInterface gameInterface;
    private Properties gameProperties;

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
        this.gameInterface = gameInterface;
        this.enterNameInterfaceController = this.gameInterface.enterNameController;
        this.questionInterfaceController = this.gameInterface.questionController;
        this.selectCategoryInterfaceController = this.gameInterface.selectCategoryController;
        this.waitController = this.gameInterface.waitController;
        this.resultFromRoundInterfaceController = this.gameInterface.resultFromRoundController;
        this.resultFromRoundInterfaceController.setProperties(Constants.CATEGORIES, Constants.QUESTIONS, Constants.ROUNDS);
        this.thread.start();
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
                    this.getWaitController().waitPromptText.setText("Väntar på motståndare...");
                    this.gameInterface.primaryStage.setScene(this.gameInterface.waitScene);
                }
            });
        });

        // Skickar texten på markerad knapp i frågerutan till ClientHandler och hanteras av ClientProtocol
        this.getQuestionInterfaceController().acceptButton.setOnAction(event -> {
            if (this.getQuestionInterfaceController().getAcceptButtonText().equalsIgnoreCase("Svara")) {
                Request newRequest = new Request(RequestStatus.ANSWER);
                // Ger texten på knappen till Request - objektet
                newRequest.setAnswerText(Objects.requireNonNull(getQuestionInterfaceController().getSelectedToggleText()));

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

    public EnterNameInterfaceController getEnterNameInterfaceController() {
        return enterNameInterfaceController;
    }

    public QuestionInterfaceController getQuestionInterfaceController() {
        return questionInterfaceController;
    }

    public SelectCategoryInterfaceController getSelectCategoryInterfaceController() {
        return selectCategoryInterfaceController;
    }

    public ResultFromRoundInterfaceController getResultFromRoundController(){
        return resultFromRoundInterfaceController;
    }

    public WaitController getWaitController() {
        return waitController;
    }

    public GameInterface getGameInterface() {
        return this.gameInterface;
    }
}

// TODO - Få in kategorierna och x antal frågor per rond
// TODO - Poäng
// TODO - efter x antal frågor måste spelare x vänta på spelare y