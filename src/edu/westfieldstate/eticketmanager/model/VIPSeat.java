package edu.westfieldstate.eticketmanager.model;

import edu.westfieldstate.eticketmanager.model.Seat;

public class VIPSeat implements Seat
{ //Should make like 50-60% more expensive (realistic)

    private int seatId;
    private String seatSection;
    private String seatRow;
    private int seatNum;
    private boolean active;
    private double price;

    public VIPSeat(int seatId, String seatSection, String seatRow, int seatNum, boolean active, double price){
        this.seatId = seatId;
        this.seatSection = seatSection;
        this.seatRow = seatRow;
        this.seatNum = seatNum;
        this.active = active;
        this.price = price;
    }
    @Override
    public int getSeatId() {
        return seatId;
    }

    @Override public String getSeatType() {
        return "vip"; //Case doesn't matter
    }

    @Override
    public String getSeatSection() {
        return seatSection;
    }

    @Override
    public String getSeatRow() {
        return seatRow;
    }

    @Override
    public int getSeatNum() {
        return seatNum;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override public String getColor() {
        return "purple";
    }

    @Override
    public String setSeatSection(String section) {
        return seatSection = section;
    }

    @Override
    public String setSeatRow(String row) {
        return seatRow = row;
    }

    @Override
    public int setSeatNum(int num) {
        return seatNum = num;
    }

    @Override
    public boolean setActive(boolean active) {
        return this.active = active;
    }
}
