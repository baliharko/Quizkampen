package Quiz.ClientSide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent question = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("question.fxml")));

        Scene questionScene = new Scene(question, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 2);
        questionScene.getStylesheets().add("styles.css");

        primaryStage.setTitle(Constants.TITLE);
        primaryStage.setScene(questionScene);

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}