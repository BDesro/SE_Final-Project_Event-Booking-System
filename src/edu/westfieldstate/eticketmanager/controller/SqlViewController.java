package edu.westfieldstate.eticketmanager.controller;

import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.util.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SqlViewController {
    @FXML
    private TextArea passwordBox;
    @FXML
    private Label errorLabel;


    public void submitPassword()
    {
        if(passwordBox.getText().isEmpty())
        {
            errorLabel.setVisible(true);
        }
        else {
            JDBC.setPassword(passwordBox.getText());
            SceneManager.switchTo(SceneID.LOGIN_SCREEN);
        }
    }

}
