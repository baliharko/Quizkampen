package Quiz.ClientSide.controllers;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Robin Martinsson
 * Date:    2020-11-26
 * Time:    10:18
 * Project: Inlämning04
 * Copyright: MIT
 */
public class SelectCategoryInterfaceController implements Serializable {

    ObjectOutputStream out;

    public ToggleButton category1Btn;
    public ToggleButton category2Btn;
    public ToggleButton category3Btn;
    public ToggleButton category4Btn;

    public ToggleGroup categoriesBtnGroup;

    public void initialize() {
        categoriesBtnGroup = new ToggleGroup();
        category1Btn.setToggleGroup(categoriesBtnGroup);
        category2Btn.setToggleGroup(categoriesBtnGroup);
        category3Btn.setToggleGroup(categoriesBtnGroup);
        category4Btn.setToggleGroup(categoriesBtnGroup);
    }

    public void setCategoriesToButtons(){
        category1Btn.setText("Vetenskap");
        category2Btn.setText("Musik");
        category3Btn.setText("Historia");
        category4Btn.setText("Film");
    }

    public void selectedCategory(ActionEvent event) throws IOException {

        if  ((event.getSource()).equals(category1Btn)) {
//            System.out.println(c1Btn.getText());
            String sendCategory = category1Btn.getText();
            out.writeObject("Category " + category1Btn);
        }
                else if  ((event.getSource()).equals(category2Btn)) {
//            System.out.println(c2Btn.getText());
            out.writeObject("Category " + category2Btn.getText());
        }
        else if  ((event.getSource()).equals(category3Btn)) {
//            System.out.println(c3Btn.getText());
            out.writeObject("Category " + category3Btn.getText());
        }
    }


}
