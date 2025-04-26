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
    private Label noEventsLabel;

    @FXML
    private ComboBox<Venue> venueSelector;

    private ObservableList<Event> tableEvents;

    @FXML
    public void initialize()
    {
        tableEvents = FXCollections.observableArrayList();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

        noEventsLabel = new Label("No Venue Selected");
        table.setPlaceholder(noEventsLabel);

       // tableEvents = Event.getAllEvents();
       // table.setItems(tableEvents);

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

    public ObservableList<Event> getPublicVenueEvents(Venue venue)
    {
        String query = "SELECT event_name, event_description, event_date " +
                       "FROM event_venue " +
                       "WHERE is_active = 1 && venue_name = ? " +
                       "ORDER BY eventDate";

        try (Connection connection = JDBC.getConnection())
        {
            if(connection != null)
            {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, venue.getVenueName());
                ResultSet rs = ps.executeQuery();
                tableEvents.clear();

                while(rs.next())
                {
                    String eventName = rs.getString("event_name");
                    String eventDescription = rs.getString("event_description");
                    LocalDate date = LocalDate.parse(rs.getString("event_date"));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
