package edu.westfieldstate.eticketmanager.controller;
import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.model.Event;
import edu.westfieldstate.eticketmanager.model.Venue;
import edu.westfieldstate.eticketmanager.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

//======================================================================================
//                 THIS CLASS CONTROLS THE GUI FOR THE ADMIN SCENE
//======================================================================================
public class AdminController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private Label date;
    @FXML
    private Label publicity;
    @FXML
    private TextArea nameTextArea;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;
    @FXML
    private Label venueName;
    @FXML
    private Label venueAddr;
    @FXML
    private ComboBox<Venue> venuePicker;
    @FXML
    private TextArea venueNameTextArea;
    @FXML
    private TextArea venueLocationTextArea;
    @FXML
    private Button saveVenueButton;
    @FXML
    private Button saveEventButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button editButton;
    @FXML
    private Button publishButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button createVenueButton;
    @FXML
    private Button createEventButton;
    @FXML
    private Label enterEventName;
    @FXML
    private Label enterEventDescription;
    @FXML
    private Label enterEventDate;
    @FXML
    private Label enterEventVenue;
    @FXML
    private Label enterVenueName;
    @FXML
    private Label enterVenueAddress;



    ArrayList<Venue> comboBoxVenues = new ArrayList<Venue>();
    private Event selectedEvent;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public int eventIndex = -1;
    private ObservableList<Event> listOfEvents = FXCollections.observableArrayList();
    private String sqlCode;


    //Does what the name says
    //Create Event Button
    public void createEvent()
    {
        selectedEvent = new Event();
        listOfEvents.add(selectedEvent);
        refreshText();
        eventIndex++;
        makeSaveEventVisible();
    }

    //Goes to the next event within the observable list
    //Next Event Button
    public void nextEvent()
    {
        if (selectedEvent==null)
        {
            makeSaveEventVisible();
            createEvent();
        }
        else {
            if (eventIndex + 1 == listOfEvents.size())
                eventIndex = 0;
            else
                eventIndex++;
            selectedEvent = listOfEvents.get(eventIndex);
            refreshText();
        }
    }

    //Updates the text shown on the admin screen to reflect the currently selected event
    private void refreshText(){
        date.setText(formatter.format(selectedEvent.getEventDate()));
        name.setText(selectedEvent.getEventName());
        description.setText(selectedEvent.getEventDescription());
        if (selectedEvent.getIsVisible())
            publicity.setText("It's public");
        else publicity.setText("It's not public");
        venueName.setText(selectedEvent.getEventVenue().getVenueName());
        venueAddr.setText(selectedEvent.getEventVenue().getVenueLocation());
        nameTextArea.setText(selectedEvent.getEventName());
        descriptionTextArea.setText(selectedEvent.getEventDescription());
        datePicker.setValue(selectedEvent.getEventDate());
        venuePicker.setValue(selectedEvent.getEventVenue());
    }
    //Changes the selected event data locally to reflect changes made within in the DB
    //without needing to pull info from the database
    private void quickUpdate()
    {
        selectedEvent.setEventName(nameTextArea.getText());
        selectedEvent.setEventDescription(descriptionTextArea.getText());
        selectedEvent.setEventDate(datePicker.getValue());
        selectedEvent.setEventVenue(venuePicker.getValue());
    }
    //Brings the user back to the login screen
    //Sign Out Button
    public void goToLogin(){
        SceneManager.switchTo(SceneID.LOGIN_SCREEN);
    }

    public void makeVenueBoxesVisible()
    {
        venueLocationTextArea.setVisible(true);
        venueNameTextArea.setVisible(true);
        saveVenueButton.setVisible(true);
        enterVenueName.setVisible(true);
        enterVenueAddress.setVisible(true);
        descriptionTextArea.setVisible(false);
        nameTextArea.setVisible(false);
        nextButton.setVisible(false);
        editButton.setVisible(false);
        publishButton.setVisible(false);
        deleteButton.setVisible(false);
        createEventButton.setVisible(false);
        createVenueButton.setVisible(false);
        venuePicker.setVisible(false);
        datePicker.setVisible(false);
        errorLabel.setText("");
        successLabel.setText("");
    }
    public void makeVenueBoxesInvisible()
    {
        venueLocationTextArea.setVisible(false);
        venueNameTextArea.setVisible(false);
        saveVenueButton.setVisible(false);
        enterVenueName.setVisible(false);
        enterVenueAddress.setVisible(false);
        descriptionTextArea.setVisible(true);
        nameTextArea.setVisible(true);
        nextButton.setVisible(true);
        editButton.setVisible(true);
        publishButton.setVisible(true);
        deleteButton.setVisible(true);
        createEventButton.setVisible(true);
        createVenueButton.setVisible(true);
        venuePicker.setVisible(true);
        datePicker.setVisible(true);
    }

    public void makeSaveEventVisible()
    {
        saveEventButton.setVisible(true);
        enterEventName.setVisible(true);
        enterEventDescription.setVisible(true);
        enterEventDate.setVisible(true);
        enterEventVenue.setVisible(true);
        nextButton.setVisible(false);
        editButton.setVisible(false);
        publishButton.setVisible(false);
        deleteButton.setVisible(false);
        createEventButton.setVisible(false);
        createVenueButton.setVisible(false);
        errorLabel.setText("");
        successLabel.setText("");
    }
    public void makeSaveEventInvisible()
    {

        saveEventButton.setVisible(false);
        enterEventName.setVisible(false);
        enterEventDescription.setVisible(false);
        enterEventDate.setVisible(false);
        enterEventVenue.setVisible(false);
        nextButton.setVisible(true);
        editButton.setVisible(true);
        publishButton.setVisible(true);
        deleteButton.setVisible(true);
        createEventButton.setVisible(true);
        createVenueButton.setVisible(true);
    }


    // ==============================================================================================
    //                                    DATA BASE FUNCTIONS
    // ==============================================================================================

    //Saves the event to the database calls validateEventName() in case the event table is changed
    //Save Event To DB Button
    public void saveEvent()
    {
        if(validateEventName() && !venuePicker.getValue().getVenueName().equals("DEFAULT VENUE")) {
            sqlCode = ("INSERT INTO event_list (event_name, event_description, " +
                    "event_date, is_active, venue_id) VALUES (TRIM(?),?,?,?,?)");
            PreparedStatement statement;
            try (Connection connection = JDBC.getConnection()){
                quickUpdate();
                statement = connection.prepareStatement(sqlCode);
                statement.setString(1, selectedEvent.getEventName());
                statement.setString(2, selectedEvent.getEventDescription());
                statement.setString(3, formatter.format(selectedEvent.getEventDate()));
                statement.setBoolean(4, selectedEvent.getIsVisible());
                statement.setInt(5,getVenueId(venuePicker.getValue()));
                int rowsLeft = statement.executeUpdate();
                if (rowsLeft > 0) {
                    errorLabel.setText("");
                    successLabel.setText("Event Saved To The DataBase");
                    refreshText();
                }
                statement.close();
                makeSaveEventInvisible();
            } catch (SQLException event) {
                successLabel.setText("");
                errorLabel.setText("Cannot Save This Event: " + event.getMessage());
            }
        }
        else {
            successLabel.setText("");
            if (venuePicker.getValue().getVenueName().equals("DEFAULT VENUE"))
                errorLabel.setText("Cannot Save This Event: Select A Venue");
            else
                errorLabel.setText("Cannot Save This Event: Name In Use ");
        }
    }

    private int getVenueId(Venue venue)
    {
        sqlCode = "SELECT venue_id FROM venues WHERE venue_name = ?";
        PreparedStatement statement;
        try (Connection connection = JDBC.getConnection()){
            statement = connection.prepareStatement(sqlCode);
            statement.setString(1, venue.getVenueName());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("venue_id");
        }
        catch (SQLException event) {
                event.printStackTrace();
                return -1;
        }
    }

    //Changes the publicity of the event non-visible events are only seen by the admins not the public
    // is_active == false means it is not visible
    //Publish Event Button
    public void publishEvent()
    {
        if (selectedEvent==null)
        {
            makeSaveEventVisible();
            createEvent();
        }
        else {
            sqlCode = ("UPDATE event_list " +
                    "SET is_active = ? " +
                    "WHERE event_name = ?");
            PreparedStatement statement;
            try (Connection connection = JDBC.getConnection()) {
                quickUpdate();
                statement = connection.prepareStatement(sqlCode);
                statement.setBoolean(1, true);
                statement.setString(2, selectedEvent.getEventName());
                statement.executeUpdate();
                selectedEvent.setIsVisible(true);
                errorLabel.setText("");
                successLabel.setText("Event Successfully Published");
                refreshText();
                statement.close();
            } catch (SQLException event) {
                successLabel.setText("");
                errorLabel.setText("Failed To Publish Event: " + event.getMessage());
            }
        }
    }

    //Edits the currently selected event
    //Edit Event Button
    public void editEvent()
    {
        if (selectedEvent==null)
        {
            makeSaveEventVisible();
            createEvent();
        }
        else {
            sqlCode = ("UPDATE event_list " +
                    "SET event_name = ?, event_description = ?, " +
                    "event_date = ?, is_active = ? " +
                    "WHERE event_name = ?");
            PreparedStatement statement;
            try (Connection connection = JDBC.getConnection()) {
                statement = connection.prepareStatement(sqlCode);
                statement.setString(1, nameTextArea.getText());
                statement.setString(2, descriptionTextArea.getText());
                statement.setString(3, formatter.format(datePicker.getValue()));
                statement.setBoolean(4, selectedEvent.getIsVisible());
                statement.setString(5, selectedEvent.getEventName());
                int rowsLeft = statement.executeUpdate();
                if (rowsLeft > 0) {
                    errorLabel.setText("");
                    successLabel.setText("Event Edited Successfully");
                    quickUpdate();
                    refreshText();
                } else {
                    successLabel.setText("");
                    errorLabel.setText("Event Could Not Be Found");
                }
                statement.close();
            } catch (SQLException event) {
                successLabel.setText("");
                errorLabel.setText("Failed To Edit Event: " + event.getMessage());
            }
        }
    }

    //Deletes the event from the database and removes it from the list of events
    //Delete Event Button
    public void deleteEvent()
    {
        if (selectedEvent==null)
        {
            makeSaveEventVisible();
            createEvent();
        }
        else {
            sqlCode = "DELETE FROM event_list WHERE event_name = ?";
            PreparedStatement statement;
            try (Connection connection = JDBC.getConnection()) {
                statement = connection.prepareStatement(sqlCode);
                statement.setString(1, selectedEvent.getEventName());
                int rowsLeft = statement.executeUpdate();
                if (rowsLeft > 0) {
                    successLabel.setText("Event Deleted Successfully!");
                    errorLabel.setText("");
                    selectedEvent = listOfEvents.get(eventIndex);
                    listOfEvents.remove(selectedEvent);
                    eventIndex--;
                    nextEvent();
                    refreshText();
                } else {
                    successLabel.setText("");
                    errorLabel.setText("Event Could Not Be Found");
                }
                statement.close();
            } catch (SQLException event) {
                successLabel.setText("");
                errorLabel.setText("Failed To Delete Event: " + event.getMessage());
            }
        }
    }

    //This will return a boolean to dictate weather the event is already in the DB or not
    //false meaning it's not a valid event name
    //true meaning it's a valid event name
    private boolean validateEventName()
    {
        boolean valid =false;
        sqlCode ="SELECT COUNT(event_id) FROM event_list WHERE event_name = ?";
        PreparedStatement statement;

        try (Connection connection = JDBC.getConnection()){
            statement = connection.prepareStatement(sqlCode);
            statement.setString(1,nameTextArea.getText());
            ResultSet rs = statement.executeQuery();
            rs.next();
            if(rs.getInt("COUNT(event_id)") == 0)
            {
                selectedEvent.setEventName(nameTextArea.getText());
                selectedEvent.setEventDescription(descriptionTextArea.getText());
                selectedEvent.setEventDate(datePicker.getValue());
                valid = true;
            }
            rs.close();
            statement.close();
        } catch (SQLException event) {
            throw new RuntimeException(event);
        }
        return valid;
    }


    //Pulls all the saved event from the DB and reformats the observable list
    //Pull Events Button
    public void pullEvents()
    {
        sqlCode ="SELECT * FROM event_venue";
        PreparedStatement statement;

        try (Connection connection = JDBC.getConnection()){
            statement = connection.prepareStatement(sqlCode);
            ResultSet rs = statement.executeQuery();
            listOfEvents.clear();
            while(rs.next())
            {
                String name = rs.getString("event_name");
                String description = rs.getString("event_description");
                LocalDate date = LocalDate.parse(rs.getString("event_date"));
                Boolean visible =rs.getBoolean("is_active");
                String vName = rs.getString("venue_name");
                String vLocation = rs.getString("address");
                selectedEvent = new Event(name,description,date,visible, new Venue(vName,vLocation));
                listOfEvents.add(selectedEvent);
                eventIndex++;
            }
            if (listOfEvents.isEmpty())
            {
                successLabel.setText("");
                errorLabel.setText("No Events Found In Database");
            }
            else {
                eventIndex = listOfEvents.size()-1;
                nextEvent();
                refreshText();
                errorLabel.setText("");
                successLabel.setText("Events Pulled From Database");
            }
            rs.close();
            statement.close();
        } catch (SQLException event) {
            successLabel.setText("");
            errorLabel.setText("Cannot Connect To Database: " + event.getMessage());
        }
    }

    public void comboBoxInitialize(){
        sqlCode ="SELECT venue_name, address FROM venues";
        PreparedStatement statement;
        if(!venuePicker.getItems().isEmpty())
        {
            venuePicker.getItems().clear();
            comboBoxVenues.clear();
        }
        try (Connection connection = JDBC.getConnection()) {
            statement = connection.prepareStatement(sqlCode);
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                {
                    successLabel.setText("");
                    errorLabel.setText("No Venues Can Be Found In Database ");
                    makeVenueBoxesVisible();
                    errorLabel.setText("You Must Add A Venue Before Continuing");
                }
            else {
                do
                    comboBoxVenues.add(new Venue(rs.getString("venue_name"),rs.getString("address")));
                while (rs.next());
                venuePicker.getItems().addAll(comboBoxVenues);
            }
            rs.close();
            statement.close();
        }
        catch (SQLException event) {
            successLabel.setText("");
            errorLabel.setText("Cannot Connect To Database: " + event.getMessage());
        }
    }

    public void saveVenue()
    {
        sqlCode="INSERT INTO venues VALUE (default, TRIM(?), TRIM(?))";
        PreparedStatement statement;
        if (!venueNameTextArea.getText().isEmpty() && !venueLocationTextArea.getText().isEmpty()) {
            try (Connection connection = JDBC.getConnection()) {
                statement = connection.prepareStatement(sqlCode);
                statement.setString(1, venueNameTextArea.getText());
                statement.setString(2, venueLocationTextArea.getText());
                int rowsLeft = statement.executeUpdate();
                if (rowsLeft > 0) {
                    errorLabel.setText("");
                    successLabel.setText("Venue Saved To The DataBase");
                    makeVenueBoxesInvisible();
                }
                statement.close();
                comboBoxInitialize();
            } catch (SQLException event) {
                successLabel.setText("");
                errorLabel.setText("Cannot Save Venue: " + event.getMessage());
            }
        }
        else {
            successLabel.setText("");
            errorLabel.setText("Cannot Save Venue Please Fill In All Boxes");
        }
    }



    // ==============================================================================================
    //                                    INITIALIZE UPON OPENING
    // ==============================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pullEvents();
        comboBoxInitialize();
    }
    // ==============================================================================================
    //                                    HELP BUTTON
    // ==============================================================================================

    public void helpBox(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/westfieldstate/eticketmanager/view/help-view.fxml"));
            Parent root = loader.load();
            Stage helpStage = new Stage();
            helpStage.setTitle("Help Screen");
            helpStage.setScene(new Scene(root));
            helpStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}