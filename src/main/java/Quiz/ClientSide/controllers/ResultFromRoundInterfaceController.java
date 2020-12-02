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
    public VBox centerBox;
    private GridPane centerGrid;


    public void initialize() {
        fillGrid();
    }

    public void fillGrid() {
        centerBox.setSpacing(20);

        for (int i = 0; i < Constants.ROUNDS; i++) {
            FlowPane flowPane = new FlowPane();
            flowPane.setHgap((Constants.WINDOW_WIDTH / (Constants.QUESTIONS * 2) + 1) / 8);
            flowPane.setAlignment(Pos.CENTER);
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
                    flowPane.getChildren().add(sPane);
                } else {
                    Button button = new Button();
                    button.setFocusTraversable(false);
                    flowPane.getChildren().add(button);
                }
            }
            centerBox.getChildren().add(flowPane);
        }
    }

    public void updateResults(boolean[] playerResults, boolean[] opponentResults, int roundNumber) {
        this.centerBox.getChildren().remove(roundNumber);
        FlowPane updatedPane = new FlowPane();
        updatedPane.setHgap((Constants.WINDOW_WIDTH / (Constants.QUESTIONS * 2) + 1) / 8);
        updatedPane.setAlignment(Pos.CENTER);
        Text text = new Text("Rond " + roundNumber);
        text.setStyle("-fx-font-size: 20px;"
                + "-fx-font-family: Courier;"
                + "-fx-fill: white");

        for (int i = 0, j = 0; i < (2 * Constants.QUESTIONS + 1); i++) {
            if (i == Constants.QUESTIONS) {
                StackPane sPane = new StackPane();
                sPane.setAlignment(Pos.CENTER);
                sPane.setMaxWidth(Constants.WINDOW_WIDTH / 3);
                sPane.setMinWidth(Constants.WINDOW_WIDTH / 3);
                sPane.setPrefWidth(Constants.WINDOW_WIDTH / 3);
                sPane.getChildren().add(text);
                updatedPane.getChildren().add(sPane);
            } else if (i < Constants.QUESTIONS) {
                Button button = new Button();
                button.setFocusTraversable(false);
                String style = playerResults[i] ? String.format("-fx-background-color: %s", Constants.COLOR_TRUE)
                        : String.format("-fx-background-color: %s", Constants.COLOR_FALSE);

                button.setStyle(style);
                updatedPane.getChildren().add(button);
            } else {
                Button button = new Button();
                button.setFocusTraversable(false);
                String style = opponentResults[j] ? String.format("-fx-background-color: %s", Constants.COLOR_TRUE)
                        : String.format("-fx-background-color: %s", Constants.COLOR_FALSE);

                button.setStyle(style);
                updatedPane.getChildren().add(button);
                j++;
            }
        }
        this.centerBox.getChildren().add(roundNumber, updatedPane);
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
