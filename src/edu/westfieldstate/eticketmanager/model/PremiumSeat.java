package edu.westfieldstate.eticketmanager.model;

public class PremiumSeat implements Seat
{
    //Didn't discuss this type of seat much, assuming it will be more expensive and different color
    private int seatId;
    private String seatSection;
    private String seatRow;
    private int seatNum;
    private boolean active;
    private double price;

    public PremiumSeat(int seatId, String seatSection, String seatRow, int seatNum, boolean active, double price){
        this.seatId = seatId;
        this.seatSection = seatSection;
        this.seatRow = seatRow;
        this.seatNum = seatNum;
        this.active = active;
        this.price = price * 1.2;
    }
    @Override
    public int getSeatId() {
        return seatId;
    }

    @Override public String getSeatType() {
        return "premium";
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
        return "gold"; //Making it gold, coulv'e put it for VIP color but VIP seems more fancy than gold, thiking iridium
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
