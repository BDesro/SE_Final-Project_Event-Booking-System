package edu.westfieldstate.eticketmanager.model;

import edu.westfieldstate.eticketmanager.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event {

    private String eventName;
    private String eventDescription;
    private LocalDate eventDate;
    private Boolean isVisible;
    private Venue eventVenue;

    public Event()
    {
        eventName = "Default Name";
        eventDescription = "Default Description";
        eventDate = LocalDate.now();
        isVisible = false;
        eventVenue = new Venue();
    }
    public Event(String name, String description, LocalDate date, Boolean visible, Venue venue)
    {
        eventName = name;
        eventDescription = description;
        eventDate = date;
        isVisible = visible;
        eventVenue = venue;
    }






    public LocalDate getEventDate() {
        return eventDate;
    }
    public String getEventName() {
        return eventName;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public Boolean getIsVisible(){
        return isVisible;
    }

    public void setEventName(String newName) {
        if (!newName.isEmpty())
            eventName = newName;
    }
    public void setEventDescription(String newDescription) {
        if (!newDescription.isEmpty())
            eventDescription = newDescription;
    }
    public void setEventDate(LocalDate newDate) {
        if (newDate != null)
            eventDate = newDate;
    }
    public void setIsVisible(Boolean newVisibility)
    {
        isVisible = newVisibility;
    }

    public String toString() {
        return getEventName() + " " + getEventDescription() + " " + getEventDate();
    }


    public Venue getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(Venue eventVenue) {
        if(eventVenue!=null)
            this.eventVenue = eventVenue;
    }
}
