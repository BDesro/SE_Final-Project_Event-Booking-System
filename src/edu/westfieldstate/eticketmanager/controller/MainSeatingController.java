package edu.westfieldstate.eticketmanager.controller;
import edu.westfieldstate.eticketmanager.model.Seat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.HashMap;
import java.util.Map;

public class MainSeatingController { //This class is going to handle the mutliple seats, rows, and labels for the
    //seating screen

    @FXML
    private GridPane seatGrid;

    public void initialize() {
        seatGrid.getChildren().clear();
        Map<Character, Integer> sectionColumnTracker = new HashMap<>();
        try {
            SeatController seatController = new SeatController();
            seatController.generateSeats(5, 10);
            for (Seat seat : seatController.getAllSeats()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/westfieldstate/eticketmanager/view/seat.fxml"));
                VBox seatNode = loader.load();

                SeatController controller = loader.getController();
                controller.setSeat(seat);

                int row, col;
                char sectionChar = Character.toUpperCase(seat.getSeatSection().charAt(0));
                row = sectionChar - 'A';
                col = sectionColumnTracker.getOrDefault(sectionChar, 0);
                sectionColumnTracker.put(sectionChar, col + 1);

                seatGrid.add(seatNode, col, row);
                GridPane.setMargin(seatNode, new Insets(10));
            }
        } catch (Exception e) {
            System.out.println("Error loading seats from database");
        }

    }

}
