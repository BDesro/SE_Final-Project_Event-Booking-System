package edu.westfieldstate.eticketmanager.model;

import edu.westfieldstate.eticketmanager.model.PremiumSeat;
import edu.westfieldstate.eticketmanager.model.Seat;

public class SeatFactory { //Simple factory
    public static Seat createSeat(int seatId, String section, String row, int number, String type, boolean active, double price) {
        switch (type) {
            case "standard":
                return new StandardSeat(seatId, section, row, number, active, price);
            case "premium":
                return new PremiumSeat(seatId, section, row, number, active, price);
            case "vip":
                return new VIPSeat(seatId, section, row, number, active, price);
        }
        return null; //Should change to default
    }
}
