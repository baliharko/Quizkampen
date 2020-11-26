package Quiz.ClientSide.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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

    public void initialize() {
        group = new ToggleGroup();

        toggle1.setToggleGroup(group);
        toggle2.setToggleGroup(group);
        toggle3.setToggleGroup(group);
        toggle4.setToggleGroup(group);
    }

    public void setConnectionStatus(String s) {
        this.connectionStatus.setText(s);
    }

    public void setQuestionText(String questionText) {
            this.questionText.setText(questionText);
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
}

