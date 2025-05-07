package edu.westfieldstate.eticketmanager.util;


import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.model.Event;
import edu.westfieldstate.eticketmanager.model.Seat;
import edu.westfieldstate.eticketmanager.model.Venue;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SharedSeatingInfo {
    private static ListView<Seat> seatList;
    private static Double totalPrice;
    private static Venue venue;
    private static Event event;

    private SharedSeatingInfo(){}


    public static ListView<Seat> getSeatList() {
        return seatList;
    }

    public static void setSeatList(ListView<Seat> seatList) {
        SharedSeatingInfo.seatList = seatList;
    }

    public static void clearSeatList() {
        seatList.getItems().clear();
    }

    public static Double getTotalPrice() {
        return totalPrice;
    }

    public static void setTotalPrice(Double totalPrice) {
        SharedSeatingInfo.totalPrice = totalPrice;
    }

    public static Event getEvent() {
        return event;
    }

    public static void setEvent(Event event) {
        SharedSeatingInfo.event = event;
    }

    public static Venue getVenue() {
        return venue;
    }

    public static void setVenue(Venue venue) {
        SharedSeatingInfo.venue = venue;
    }

    public static int getVenueID()
    {
        String query = "SELECT venue_id FROM venues WHERE venue_name =?;";
        try(Connection connection = JDBC.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, venue.getVenueName());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static int getEventID()
    {
        String query = "SELECT event_id FROM event_list WHERE event_name =?;";
        try(Connection connection = JDBC.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,event.getEventName());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
