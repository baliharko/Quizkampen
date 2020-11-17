package Quiz.ClientSide;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GameInterfaceController {

    public Text questionText;

    public ToggleButton button1; // set text to answers
    public ToggleButton button2;
    public ToggleButton button3;
    public ToggleButton button4;

    public Text connectionStatusMessage;

    public StackPane questionArea;
    public GridPane buttonGrid;

    public ToggleGroup group;

    public ToggleButton toggle1;
    public ToggleButton toggle2;
    public ToggleButton toggle3;
    public ToggleButton toggle4;

    public void initialize() {
        group = new ToggleGroup();

        toggle1.setToggleGroup(group);
        toggle2.setToggleGroup(group);
        toggle3.setToggleGroup(group);
        toggle4.setToggleGroup(group);

    }
}

