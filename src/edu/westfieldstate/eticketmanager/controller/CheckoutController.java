package edu.westfieldstate.eticketmanager.controller;
import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.model.Seat;
import edu.westfieldstate.eticketmanager.model.User;
import edu.westfieldstate.eticketmanager.resources.Avatar;
import edu.westfieldstate.eticketmanager.util.JDBC;
import edu.westfieldstate.eticketmanager.util.SharedSeatingInfo;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;

import java.sql.*;
import java.util.ArrayList;

public class CheckoutController {

    @FXML ListView<Seat> checkoutSeats;
    @FXML Label checkoutPrice;
    @FXML CheckBox yes;
    @FXML CheckBox no;
    @FXML Button confirmPurchase;
    private User activeUser;
    private double checkPrice;

    @FXML
    public void initialize(){
        checkoutSeats.getItems().addAll(SharedSeatingInfo.getSeatList().getItems());
        checkPrice = SharedSeatingInfo.getTotalPrice();
        checkoutPrice.setText("Total Checkout Price: $" + checkPrice);
        getActiveUser();

        confirmPurchase.setOnAction(e -> {
            if (yes.isSelected()) {
                ObservableList<Seat> seats = checkoutSeats.getItems();
                for (Seat seat : seats) {
                    String query = "INSERT INTO bookings (user_id, seat_id, event_id, venue_id) VALUES (?,?,?,?)";
                    try (Connection connection = JDBC.getConnection()) {

                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, getUserID());
                        statement.setInt(2, seat.getSeatId());
                        statement.setInt(3, SharedSeatingInfo.getEventID());
                        statement.setInt(4, SharedSeatingInfo.getVenueID());
                        statement.executeUpdate();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }

                SceneManager.switchTo(SceneID.PURCHASE_DONE);
            }
        }
        );

    }

    public void getActiveUser()
    {
        String query = "SELECT * FROM users WHERE is_active = 1";

        try(Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                String username = rs.getString("username");
                String email = rs.getString("email_address");
                String nickname = rs.getString("nickname");
                Avatar avatar = Avatar.valueOf(rs.getString("avatar"));
                activeUser = new User(username, email,nickname,avatar); //Will add feature to get avatar, harder than I thought
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getUserID()
    {
        String query = "SELECT user_id FROM users WHERE username = ?";
        try(Connection connection = JDBC.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, activeUser.getUsername());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}
