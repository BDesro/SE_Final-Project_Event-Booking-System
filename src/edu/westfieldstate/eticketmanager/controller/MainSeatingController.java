package edu.westfieldstate.eticketmanager.controller;
import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.model.Seat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.*;

public class MainSeatingController { //This class is going to handle the mutliple seats, rows, and labels for the
    //seating screen

    private double total = 0.00;
    @FXML Button goToCheck;
    @FXML
    private GridPane seatLayout;
    @FXML private Label totalPrint;
    @FXML
    ListView seatList;
    public void updateTotal() {
        totalPrint.setText("Total: $" + total);
    }

    public double getTotal(){
        return total;
    }

    public void increase(double seatPrice) {
        total += seatPrice;
        updateTotal();
    }

    public void decrease(double seatPrice) {
        total -= seatPrice;
        updateTotal();
    }

    public void displaySeats() {
        List<Seat> selected = SeatController.getSelectSeat();
        List<String> seatDescriptions = new ArrayList<>();
        for (Seat seat : selected) {
            String description = "Section " + seat.getSeatSection()
                    + " Row " + seat.getSeatRow()
                    + " Seat " + seat.getSeatNum();
            seatDescriptions.add(description);
        }

        seatList.getItems().setAll(seatDescriptions);
    }

    public ListView allSeats (){
        return seatList;
    }

    @FXML
    public void initialize() {
        seatLayout.getChildren().clear();
        Map<Character, Integer> sectionColumnTracker = new HashMap<>();
        try {
            SeatController seatController = new SeatController();
            seatController.generateSeats(5, 10);
            for (Seat seat : seatController.getAllSeats()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/westfieldstate/eticketmanager/view/seat.fxml"));
                VBox seatNode = loader.load();

                SeatController controller = loader.getController();
                controller.setSeat(seat, this);

                //Seats will print in a set square for every venue
                int row, col;
                char sectionChar = Character.toUpperCase(seat.getSeatSection().charAt(0));
                row = sectionChar - 'A';
                col = sectionColumnTracker.getOrDefault(sectionChar, 0);
                sectionColumnTracker.put(sectionChar, col + 1);

                seatLayout.add(seatNode, col, row);
                GridPane.setMargin(seatNode, new Insets(10));
            }
        } catch (Exception e) {
            System.out.println("Error loading seats from database");
        }
        goToCheck.setOnAction(e-> SceneManager.switchTo(SceneID.CHECKOUT));

    }

}
