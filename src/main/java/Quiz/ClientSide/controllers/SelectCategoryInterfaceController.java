package Quiz.ClientSide.controllers;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SelectCategoryInterfaceController {

    public Text categoryHeader;

    public Button categoryButton1;
    public Button categoryButton2;
    public Button categoryButton3;

    public List<Button> categoryButtons;

    ObjectOutputStream out;

    public void initialize() {

        this.categoryButtons = new ArrayList<>();
        this.categoryButtons.add(this.categoryButton1);
        this.categoryButtons.add(this.categoryButton2);
        this.categoryButtons.add(this.categoryButton3);
    }
}
