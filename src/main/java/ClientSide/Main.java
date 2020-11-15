package ClientSide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Constants constants = new Constants();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample.fxml")));
        primaryStage.setTitle(Constants.TITLE);
        primaryStage.setScene(new Scene(root, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 2));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}