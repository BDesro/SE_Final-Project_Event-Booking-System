package edu.westfieldstate.eticketmanager.model;

public interface Seat { //Possible example of strategy pattern
    //Columns from the database table
    int getSeatId();
    String getSeatType();
    String getSeatSection(); //Could change to char
    String getSeatRow();
    int getSeatNum();
    boolean isActive();
    double getPrice();
    String getColor();

    //Setters for generating multiple seats and setting seats to if they are available or not
    String setSeatSection(String section);
    String setSeatRow(String row);
    int setSeatNum(int num);
    boolean setActive(boolean active);

}
