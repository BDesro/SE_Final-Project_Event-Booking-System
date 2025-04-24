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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Event()
    {
        eventName = "Default Name";
        eventDescription = "Default Description";
        eventDate = LocalDate.now();
        isVisible = false;
    }
    public Event(String name, String description, LocalDate date, Boolean visible)
    {
        eventName = name;
        eventDescription = description;
        eventDate = date;
        isVisible = visible;
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

    public static ObservableList<Event> getAllEvents()
    {
        ObservableList<Event> events = FXCollections.observableArrayList();

        String query = "SELECT event_name, event_description, event_date, is_active " +
                       "FROM event_list ";
                  // + "WHERE is_active";    I'm commenting this until the active/inactive works right

        try (Connection connection = JDBC.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery())
        {
            while(rs.next())
            {
                events.add(new Event(
                        rs.getString("event_name"),
                        rs.getString("event_description"),
                        LocalDate.parse(rs.getString("event_date")),
                        rs.getBoolean("is_active")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return events;
    }
}
