package Quiz.ClientSide.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void setCategoriesToButtons() {
//        category1Btn.setText("Vetenskap");
//        category2Btn.setText("Musik");
//        category3Btn.setText("Historia");
//        category4Btn.setText("Film");
    }

    /*
    public void selectedCategory(ActionEvent event) throws IOException {

        if ((event.getSource()).equals(category1Btn)) {
//            System.out.println(c1Btn.getText());
            String sendCategory = category1Btn.getText();
            out.writeObject("Category" + category1Btn);
        } else if ((event.getSource()).equals(category2Btn)) {
//            System.out.println(c2Btn.getText());
            out.writeObject("Category" + category2Btn.getText());
        } else if ((event.getSource()).equals(category3Btn)) {
//            System.out.println(c3Btn.getText());
            out.writeObject("Category" + category3Btn.getText());
        }
    }

     */

 //   @FXML
/*    public String getSelectedCategory(){
        return Objects.requireNonNull(((ToggleButton) this.categoriesBtnGroup.getSelectedToggle()).getText());
    }

 */

}
