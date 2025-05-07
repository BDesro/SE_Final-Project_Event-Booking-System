package edu.westfieldstate.eticketmanager.controller;

import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.util.GifUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class purchaseImage {

    @FXML
    ImageView checkMark;
    @FXML
    Button backToGeneral;
    @FXML ImageView gifView;

    @FXML
    public void initialize(){
        checkMark.setImage(new Image(getClass().getResource("/edu/westfieldstate/eticketmanager/resources/greenCheckMark.png").toExternalForm()));
        backToGeneral.setOnAction(e -> SceneManager.switchTo(SceneID.GENERAL_SCREEN));
        GifUtil.loadRandomGifIntoImageView(gifView, "celebrate");
    }
}
