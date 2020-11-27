package Quiz.ClientSide;

import Quiz.ClientSide.controllers.EnterNameInterfaceController;
import Quiz.ClientSide.controllers.QuestionInterfaceController;
import Quiz.ClientSide.controllers.SelectCategoryInterfaceController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

public class GameInterface extends Application {

    Client client;
    String playerName;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Fönstret där man väljer kategori
        FXMLLoader selectCategoryLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("selectCategory.fxml")));
        Parent selectCategory = selectCategoryLoader.load();
        SelectCategoryInterfaceController selectCategoryController = selectCategoryLoader.getController();
        Scene categoryScene = new Scene(selectCategory, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        // Fönstret innehållande frågan och de 4 svarsalternativen
        FXMLLoader questionLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("question.fxml")));
        Parent question = questionLoader.load();
        QuestionInterfaceController questionController = questionLoader.getController();
        Scene questionScene = new Scene(question, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        // Fönstret där man anger sitt namn - visas först?
        FXMLLoader enterNameLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("enterName.fxml")));
        Parent enterName = enterNameLoader.load();
        EnterNameInterfaceController enterNameController = enterNameLoader.getController();
        Scene enterNameScene = new Scene(enterName, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);


//        new Game(this);

        /**
         * Game innehåller:
         * Client, alla controllers, GameInterface
         *
         * Vid start läser Game in från .properties-fil
         *
         *
         */


        // Ange namn - fönstret
        primaryStage.setScene(enterNameScene);
        enterNameController.enterNameField.setOnAction(event -> {
            Platform.runLater(() -> {
                this.playerName = enterNameController.getEnterNameFieldText();
                enterNameController.enterNameField.setText("");

                if (!playerName.isBlank() && playerName != null) {

                    // Ger Client tillgång till kontrollern för GUI
                    this.client = new Client(questionController, this.playerName);
                    primaryStage.setScene(categoryScene);
                }
            });
        });

        // Välj kategori
        for (Button b : selectCategoryController.categoryButtons) {
            b.setOnAction(event -> {
                System.out.println(b.getText()); // Skickas till databasen och får tillbaka frågor i vald kategori.
            });
        }

        categoryScene.getStylesheets().add("styles.css");
        questionScene.getStylesheets().add("styles.css");
        enterNameScene.getStylesheets().add("styles.css");

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Closing game interface.");
            System.exit(0);
        });

        primaryStage.setTitle(Constants.TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}