package Quiz.ClientSide.controllers;

import Quiz.ClientSide.Constants;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
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

    }

    public void setConnectionStatus(String s) {
        this.connectionStatus.setText(s);
    }

    public void setQuestionText(String questionText) {
            this.questionText.setText(questionText);
    }

    public void setToggleButtonColor(boolean isRightAnswer) {
        String style = isRightAnswer ? Constants.COLOR_TRUE
                : Constants.COLOR_FALSE;

        for (ToggleButton tb : toggleButtonList) {
            tb.setMouseTransparent(true);
        }

        ((ToggleButton)group.getSelectedToggle()).setStyle("button-selected-color: " + style);
    }

    public void refreshButtons() {

        ((ToggleButton)group.getSelectedToggle()).setStyle(String.format("button-selected-color: %s", Constants.COLOR_SELECTED));

        for (ToggleButton tb : toggleButtonList) {
            tb.setMouseTransparent(false);
            tb.setSelected(false);
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

