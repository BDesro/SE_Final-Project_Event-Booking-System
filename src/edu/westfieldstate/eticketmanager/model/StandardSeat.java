package edu.westfieldstate.eticketmanager.model;

import edu.westfieldstate.eticketmanager.model.Seat;

public class StandardSeat implements Seat
{ //For now we are assuming price and color is what differentiates each seat
    private int seatId;
    private String seatSection;
    private String seatRow;
    private int seatNum;
    private double price;

    public StandardSeat(int seatId, String seatSection, String seatRow, int seatNum, double price){
        this.seatId = seatId;
        this.seatSection = seatSection;
        this.seatRow = seatRow;
        this.seatNum = seatNum;
        this.price = price;
    }


    @Override
    public int getSeatId() {
        return seatId;
    }

    @Override public String getSeatType() {
        return "standard";
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
    public double getPrice() {
        return price;
    }

    @Override public String getColor() {
        return "lightblue"; //Standard seats are goin to be light blue
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

    public String toString()
    {
        return "Section:"+getSeatSection()+ " Row:"+ getSeatRow()+ " Number:" + getSeatNum();
    }
}
