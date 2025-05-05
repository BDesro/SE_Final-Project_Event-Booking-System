package edu.westfieldstate.eticketmanager.controller;

import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class purchaseImage {

    @FXML
    ImageView checkMark;
    @FXML
    Button backToGeneral;

    @FXML
    public void initialize(){
        checkMark.setImage(new Image(getClass().getResource("/greenCheckMark.png").toExternalForm()));
        backToGeneral.setOnAction(e -> SceneManager.switchTo(SceneID.GENERAL_SCREEN));
    }
}
