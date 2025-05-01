package edu.westfieldstate.eticketmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelpViewController {
    @FXML
    private Label helpLabel;

    public void helpAdmin(){
        helpLabel.setText("You Don't Need Help");
    }

    public void helpCheckOut(){
        helpLabel.setText("You Don't Need Help");
    }
    public void helpLogIn(){
        helpLabel.setText("You Don't Need Help");
    }
    public void helpUser(){
        helpLabel.setText("You Don't Need Help");
    }
    public void helpGeneral(){
        helpLabel.setText("You Don't Need Help");
    }
}
