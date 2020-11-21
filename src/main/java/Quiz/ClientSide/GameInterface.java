package Quiz.ClientSide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Objects;

public class GameInterface extends Application {

    Client client;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader questionLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("question.fxml")));
        Parent question = questionLoader.load();
        GameInterfaceController questionController = questionLoader.getController();

        Scene questionScene = new Scene(question, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        questionScene.getStylesheets().add("styles.css");

        primaryStage.setTitle(Constants.TITLE);
        primaryStage.setScene(questionScene);

        this.client = new Client(questionController);

        primaryStage.setResizable(false);
        primaryStage.show();

        questionController.acceptButton.setOnAction(event -> {
            String answer = ((ToggleButton) questionController.group.getSelectedToggle()).getText();
            System.out.println(answer);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}