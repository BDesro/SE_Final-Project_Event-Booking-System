package edu.westfieldstate.eticketmanager.controller;

import edu.westfieldstate.eticketmanager.model.Seat;
import edu.westfieldstate.eticketmanager.model.SeatFactory;
import edu.westfieldstate.eticketmanager.util.JDBC;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.sql.*;
import java.util.ArrayList;

public class SeatController { //This class is setting up the shape each individual seat will look like and how
    //they each should interact with the user mouse and clicking
    @FXML private VBox seatPane;
    @FXML private Rectangle base;
    @FXML private Rectangle backrest;
    @FXML private Rectangle leftArmRest;
    @FXML private Rectangle rightArmRest;
    @FXML private Text seatLabel;

    private int seatCount = 1;

    private Seat seat;
    private boolean isReserved = false; //Similar to isActive
    private Tooltip tooltip;

    public void initialize() {
        tooltip = new Tooltip(); //Tooltip (check on Oracle) is a tool that shows additional info
        //When you hover over the object you install it to
        //Can be modified to show pricing for later
        Tooltip.install(seatPane, tooltip);
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
        seatLabel.setText(seat.getSeatSection() + seat.getSeatRow() + seat.getSeatNum());

        Color seatColor = Color.valueOf(seat.getColor()); // use subclass-defined color
        base.setFill(seatColor);
        backrest.setFill(seatColor);
        leftArmRest.setFill(seatColor);
        rightArmRest.setFill(seatColor);
        tooltip.setText(getText());
    }

    @FXML
    private void handleClick(MouseEvent event) { //edu.westfieldstate.eticketmanager.model.Event for future refactoring where if the user clicks on a seat
        //it will change color to represent reserved or available
        //Will also change what it shows when you hover over it from seat id (available or reserved)
        isReserved = !isReserved;

        Color newColor;
        if (isReserved) {
            newColor = Color.DARKGREY; //It was this or SALMON because those colors stuck out to me
            seat.setActive(false);
        } else {
            newColor = Color.valueOf(seat.getColor());
            seat.setActive(true);
        }
        base.setFill(newColor);
        backrest.setFill(newColor);
        leftArmRest.setFill(newColor);
        rightArmRest.setFill(newColor);
        tooltip.setText(getText());
    }

    private String getText() {
        String status;
        if (isReserved) {
            status = "Reserved";
        } else {
            status = "Available";
        }

        String type = seat.getSeatType();
        String section = seat.getSeatSection();
        String row = seat.getSeatRow();
        int number = seat.getSeatNum();
        double price = seat.getPrice();

        String text = "Seat: " + section + row + number + "\n";
        text += "Type: " + type + "\n";
        text += "Price: $" + price + "\n";
        text += "Status: " + status;

        return text;
    }

    public ArrayList<Seat> getAllSeats(){
        String line = "SELECT * FROM seats";
        ArrayList<Seat> seats = new ArrayList<>();

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(line);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Seat seat = SeatFactory.createSeat(
                        rs.getInt("seat_id"),
                        rs.getString("seat_section"),
                        rs.getString("seat_row"),
                        rs.getInt("seat_number"),
                        rs.getString("seat_type"),
                        rs.getBoolean("is_active"),
                        rs.getDouble("price")
                );
                seats.add(seat);
            }
        } catch (SQLException e) {
           System.out.println("Problem with connection");
        }

        return seats;
    }

    private boolean seatsExist() {
        String checkSQL = "SELECT COUNT(*) FROM seats";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(checkSQL);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void generateSeats(int sections, int rows) {
        if(seatsExist()){
            return;
        }
        seatCount = 1;
        String insertSQL = "INSERT INTO seats (seat_section, seat_row, seat_number, seat_type, is_active, price) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSQL)) {

            for (int sec = 0; sec < sections; sec++) {
                char section = (char) ('A' + sec);
                for (int r = 0; r < rows; r++) {
                    char row = (char) ('A' + r);
                        String type;
                        double price = 20;
                        if (section == 'E') {
                            type = "vip";
                        } else if (row >= 'E' && row <= 'F') {
                            type = "premium";
                        } else {
                            type = "standard";
                        }

                        ps.setString(1, String.valueOf(section));
                        ps.setString(2, String.valueOf(row));
                        ps.setInt(3, seatCount++);
                        ps.setString(4, type);
                        ps.setBoolean(5, false);
                        ps.setDouble(6, price);
                        //Our seat numbers are unique
                        ps.addBatch();
                }
            }

            ps.executeBatch();
            System.out.println("Checking that set s generated good, seat count: " + seatCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
