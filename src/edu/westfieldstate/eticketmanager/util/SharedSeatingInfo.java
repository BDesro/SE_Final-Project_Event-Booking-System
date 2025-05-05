package edu.westfieldstate.eticketmanager.util;

import javax.swing.text.html.ListView;

public class SharedSeatingInfo {
    private static ListView seatList;
    private static Double totalPrice;

    private SharedSeatingInfo(){}


    public static ListView getSeatList() {
        return seatList;
    }

    public static void setSeatList(ListView seatList) {
        SharedSeatingInfo.seatList = seatList;
    }

    public static Double getTotalPrice() {
        return totalPrice;
    }

    public static void setTotalPrice(Double totalPrice) {
        SharedSeatingInfo.totalPrice = totalPrice;
    }
}
