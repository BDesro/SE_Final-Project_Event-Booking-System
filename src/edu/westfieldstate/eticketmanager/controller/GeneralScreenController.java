package edu.westfieldstate.eticketmanager.controller;

import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.model.Event;
import edu.westfieldstate.eticketmanager.model.Venue;
import edu.westfieldstate.eticketmanager.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;

public class GeneralScreenController
{
    @FXML
    private TableView<Event> table;
    @FXML
    private TableColumn<Event, String> nameColumn;
    @FXML
    private TableColumn<Event, String> descriptionColumn;
    @FXML
    private TableColumn<Event, LocalDate> dateColumn;

    @FXML
    private ComboBox<Venue> venueSelector;

    private ObservableList<Event> tableEvents;

    @FXML
    public void initialize()
    {
        initializeVenueSelector();
        tableEvents = FXCollections.observableArrayList();
        table.setPlaceholder(new Label("No Venue Selected"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

        // Makes table rows (events) clickable. Clicking currently just prints event name
        table.setRowFactory(tv ->
        {
            TableRow<Event> row = new TableRow<>();
            row.setOnMouseClicked(event ->
            {
                if(!row.isEmpty() && event.getClickCount() == 1)
                {
                    Event selected = row.getItem();
                    System.out.println("Selected: " + selected.getEventName());
                    // Can use this to trigger seating screen, etc. later
                }
            });
            return row;
        });
    }

    public void backToLogin(ActionEvent e){
        SceneManager.switchTo(SceneID.LOGIN_SCREEN);
    }

    public void initializeVenueSelector()
    {
        String query ="SELECT venue_name, address " +
                      "FROM venues";

        if(!venueSelector.getItems().isEmpty())
            venueSelector.getItems().clear();

        try(Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                venueSelector.getItems().add(new Venue(rs.getString("venue_name"), rs.getString("address")));
            }
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void pullPublicVenueEvents(Venue venue)
    {
        tableEvents.clear();

        if (venue == null)
        {
            table.setPlaceholder(new Label("No Venue Selected"));
            return;
        }

        String query = "SELECT * FROM event_venue " +
                       "WHERE is_active = 1 && venue_name = ? " +
                       "ORDER BY event_date";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setString(1, venue.getVenueName());
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                String eventName = rs.getString("event_name");
                String eventDescription = rs.getString("event_description");
                LocalDate date = LocalDate.parse(rs.getString("event_date"));
                Boolean active = rs.getBoolean("is_active");
                String venueName = rs.getString("venue_name");
                String address = rs.getString("address");
                tableEvents.add(new Event(eventName, eventDescription, date, active, new Venue(venueName, address)));
            }
            rs.close();

            if(tableEvents.isEmpty())
                table.setPlaceholder(new Label("No Events Available for this Venue"));

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void updateTable(ActionEvent e)
    {
        pullPublicVenueEvents(venueSelector.getSelectionModel().getSelectedItem());
        table.setItems(tableEvents);
    }
}
