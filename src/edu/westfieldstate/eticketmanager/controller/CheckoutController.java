package edu.westfieldstate.eticketmanager.controller;
import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.util.SharedSeatingInfo;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;

public class CheckoutController {

    @FXML ListView checkoutSeats;

    @FXML Label checkoutPrice;
    private double checkPrice;

    @FXML CheckBox yes;
    @FXML
    CheckBox no;

    @FXML
    Button confirmPurchase;

    @FXML
    public void initialize(){
        checkoutSeats.getItems().addAll(SharedSeatingInfo.getSeatList().getItems());
        checkPrice = SharedSeatingInfo.getTotalPrice();
        checkoutPrice.setText("Total Checkout Price: $" + checkPrice);

        if(yes.isSelected()) //Makes sure bot the chck boxes are not selected
            no.setSelected(false);
        else
            yes.setSelected(false);

        confirmPurchase.setOnAction(e -> SceneManager.switchTo(SceneID.PURCHASE_DONE));
    }
}
