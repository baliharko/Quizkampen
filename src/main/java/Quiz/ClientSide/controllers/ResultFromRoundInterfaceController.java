package Quiz.ClientSide.controllers;

import Quiz.ClientSide.Constants;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.concurrent.Flow;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-28
 * Time:    14:35
 * Project: Inlämning04
 * Copyright: MIT
 */
public class ResultFromRoundInterfaceController {

    public VBox centerBox;
    private int categories;
    private int questions;
    private int rounds;

    public Text currentScore;
    public Text p1Name;
    public Text p2Name;
    public Button continueButton;

    // = player, round, question
    public Button p1_r1q1;
    public Button p1_r1q2;
    public Button p1_r1q3;
    public Button p2_r1q1;
    public Button p2_r1q2;
    public Button p2_r1q3;
    public AnchorPane resultsCenterPane;
    private GridPane centerGrid;


    public void initialize() {

        centerBox.setSpacing(20);

        for (int i = 0; i < Constants.ROUNDS; i++) {
            FlowPane midBox = new FlowPane();
            midBox.setHgap((Constants.WINDOW_WIDTH / (Constants.QUESTIONS * 2) + 1) / 8);
            midBox.setAlignment(Pos.CENTER);
            Text text = new Text("Rond " + i);
            text.setStyle("-fx-font-size: 20px;"
                    + "-fx-font-family: Courier;"
                    + "-fx-fill: white");

            for (int j = 0; j < (Constants.QUESTIONS * 2) + 1; j++) {
                if (j == Constants.QUESTIONS) {
                    StackPane sPane = new StackPane();
                    sPane.setAlignment(Pos.CENTER);
                    sPane.setMaxWidth(Constants.WINDOW_WIDTH / 3);
                    sPane.setMinWidth(Constants.WINDOW_WIDTH / 3);
                    sPane.setPrefWidth(Constants.WINDOW_WIDTH / 3);
                    sPane.getChildren().add(text);
                    midBox.getChildren().add(sPane);
                }
                else
                    midBox.getChildren().add(new Button());
            }

            centerBox.getChildren().add(midBox);
        }
    }

    public void setP1Name(Text p1Name) {
        this.p1Name = p1Name;
    }

    public void setP2Name(Text p2Name) {
        this.p2Name = p2Name;
    }

    public void setProperties(int categories, int questions, int rounds) {
        this.categories = categories;
        this.questions = questions;
        this.rounds = rounds;
    }
}
