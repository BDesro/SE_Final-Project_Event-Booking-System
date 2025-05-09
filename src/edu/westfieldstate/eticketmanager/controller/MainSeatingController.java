package edu.westfieldstate.eticketmanager.controller;
import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.model.Seat;
import edu.westfieldstate.eticketmanager.util.JDBC;
import edu.westfieldstate.eticketmanager.util.SharedSeatingInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class MainSeatingController { //This class is going to handle the mutliple seats, rows, and labels for the
    //seating screen

    private double total = 0.00;
    @FXML Button goToCheck;
    @FXML
    private GridPane seatLayout;
    @FXML private Label totalPrint;
    @FXML
    ListView<Seat> seatList;
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

        seatList.getItems().setAll(selected);
    }

    public ListView<Seat> allSeats (){
        return seatList;
    }

    @FXML
    public void initialize() {
        seatLayout.getChildren().clear();
        SeatController.getSelectSeat().clear();
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
                if(isSeatTaken(seat.getSeatNum()))
                    seatNode.setVisible(false);
                GridPane.setMargin(seatNode, new Insets(10));
            }
        } catch (Exception e) {
            System.out.println("Error loading seats from database");
        }
        goToCheck.setOnAction(e->
        {
            if (!seatList.getItems().isEmpty()) {
                SharedSeatingInfo.setSeatList(seatList);
                SharedSeatingInfo.setTotalPrice(total);
                SceneManager.switchTo(SceneID.CHECKOUT);
            }
        });
    }
    public Boolean isSeatTaken(int seatNum)
    {
        String query = "Select seat_id from bookings where event_id =? AND seat_id = ?";
        try(Connection connection = JDBC.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, SharedSeatingInfo.getEventID());
            statement.setInt(2,seatNum);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void goBackToGeneral(ActionEvent e)
    {
        SceneManager.switchTo(SceneID.GENERAL_SCREEN);
    }
}
