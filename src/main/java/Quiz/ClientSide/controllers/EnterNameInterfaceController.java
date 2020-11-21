package Quiz.ClientSide.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class EnterNameInterfaceController {

    public TextField enterNameField;
    public Text welcomeText;
    public AnchorPane mainPane;

    @FXML
    public String getEnterNameFieldText() {
        return enterNameField.getText();
    }
}
