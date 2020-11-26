package Quiz.ClientSide.controllers;

import javafx.scene.control.ToggleButton;

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

    private ToggleButton c1Btn;
    private ToggleButton c2Btn;
    private ToggleButton c3Btn;

    public void selectedCategory(ActionEvent event) throws IOException {

        if  ((event.getSource()).equals(c1Btn)) {
//            System.out.println(c1Btn.getText());
            String sendCategory = c1Btn.getText();
            out.writeObject("Category " + sendCategory);
        }
                else if  ((event.getSource()).equals(c2Btn)) {
//            System.out.println(c2Btn.getText());
            out.writeObject("Category " + c2Btn.getText());
        }
        else if  ((event.getSource()).equals(c3Btn)) {
//            System.out.println(c3Btn.getText());
            out.writeObject("Category " + c3Btn.getText());
        }
    }

}
