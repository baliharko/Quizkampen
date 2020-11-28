package Quiz.ClientSide.controllers;

import Quiz.ClientSide.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionInterfaceController {

    public Text questionText;

    public StackPane questionArea;
    public GridPane buttonGrid;

    public ToggleGroup group;

    public ToggleButton toggle1;
    public ToggleButton toggle2;
    public ToggleButton toggle3;
    public ToggleButton toggle4;

    public Text connectionStatus;
    public Button acceptButton;

    public List<ToggleButton> toggleButtonList;

    public void initialize() {
        toggleButtonList = new ArrayList<>();
        group = new ToggleGroup();

        toggleButtonList.add(toggle1);
        toggleButtonList.add(toggle2);
        toggleButtonList.add(toggle3);
        toggleButtonList.add(toggle4);

        for (ToggleButton tb : toggleButtonList) {
            tb.setToggleGroup(group);
        }

//
//        toggle1.setToggleGroup(group);
//        toggle2.setToggleGroup(group);
//        toggle3.setToggleGroup(group);
//        toggle4.setToggleGroup(group);
    }

    public void setConnectionStatus(String s) {
        this.connectionStatus.setText(s);
    }

    public void setQuestionText(String questionText) {
            this.questionText.setText(questionText);
    }

    public void setToggleButtonColor(boolean isRightAnswer, int buttonIndex) {
        String style = isRightAnswer ? "-fx-background-color: radial-gradient(focus-distance 0%, center 50% 50%, radius 200%, #b5f5be, #1ee700);"
                : "-fx-background-color: radial-gradient(focus-distance 0%, center 50% 50%, radius 200%, #f8cdcd, #f60c0c);";

        switch (buttonIndex) {
            case 0 -> toggle1.setStyle(style);
            case 1 -> toggle2.setStyle(style);
            case 2 -> toggle3.setStyle(style);
            case 3 -> toggle4.setStyle(style);
        }

        for (ToggleButton tb : toggleButtonList) {
            tb.setMouseTransparent(true);
        }
    }

    public void setToggleButtonsText(String[] answers) {
        toggle1.setText(answers[0]);
        toggle2.setText(answers[1]);
        toggle3.setText(answers[2]);
        toggle4.setText(answers[3]);
    }

    @FXML
    public String getSelectedToggleText() {
        return Objects.requireNonNull(((ToggleButton) this.group.getSelectedToggle()).getText());
    }

    public void setAcceptButtonText(String acceptButtonText) {
        this.acceptButton.setText(acceptButtonText);
    }

    public String getAcceptButtonText() {
        return this.acceptButton.getText();
    }
}

