package edu.westfieldstate.eticketmanager.util;


import edu.westfieldstate.eticketmanager.model.Seat;
import javafx.scene.control.ListView;

public class SharedSeatingInfo {
    private static ListView<Seat> seatList;
    private static Double totalPrice;

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
}
