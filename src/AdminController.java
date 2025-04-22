import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//======================================================================================
//                 THIS CLASS CONTROLS THE GUI FOR THE ADMIN SCENE
//======================================================================================
public class AdminController {

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

    private Event selectedEvent;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public int eventIndex = -1;
    private ObservableList<Event> listOfEvents = FXCollections.observableArrayList();
    private String sqlCode;


    //Does what the name says
    //Create Event Button
    public void createEvent(ActionEvent e)
    {
        selectedEvent = new Event();
        listOfEvents.add(selectedEvent);
        refreshText();
        eventIndex++;
    }

    //Goes to the next event within the observable list
    //Next Event Button
    public void nextEvent()
    {
        if(eventIndex + 1 == listOfEvents.size())
            eventIndex = 0;
        else
            eventIndex++;
        selectedEvent = listOfEvents.get(eventIndex);
        refreshText();
    }

    //Updates the text shown on the admin screen to reflect the currently selected event
    private void refreshText(){
        date.setText(formatter.format(selectedEvent.getEventDate()));
        name.setText(selectedEvent.getEventName());
        description.setText(selectedEvent.getEventDescription());
        if (selectedEvent.getIsVisible())
            publicity.setText("It's public");
        else publicity.setText("It's not public");
        nameTextArea.setText(selectedEvent.getEventName());
        descriptionTextArea.setText(selectedEvent.getEventDescription());
        datePicker.setValue(selectedEvent.getEventDate());
    }
    //Changes the selected event data locally to reflect changes made within in the DB
    //without needing to pull info from the database
    private void quickUpdate()
    {
        selectedEvent.setEventName(nameTextArea.getText());
        selectedEvent.setEventDescription(descriptionTextArea.getText());
        selectedEvent.setEventDate(datePicker.getValue());
    }
    //Brings the user back to the login screen
    //Sign Out Button
    public void goToLogin(ActionEvent e){
        SceneManager.switchTo(SceneID.LOGIN_SCREEN);
    }

    // ==============================================================================================
    //                                    DATA BASE FUNCTIONS
    // ==============================================================================================

    //Saves the event to the database calls validateEventName() in case the event table is changed
    //Save Event To DB Button
    public void saveEvent(ActionEvent e)
    {
        if(validateEventName()) {
            sqlCode = ("INSERT INTO event_list (event_name, event_description, " +
                    "event_date, is_active) VALUES (TRIM(?),?,?,?)");
            PreparedStatement statement = null;
            try {
                quickUpdate();
                Connection connection = JDBC.getConnection();
                statement = connection.prepareStatement(sqlCode);
                statement.setString(1, selectedEvent.getEventName());
                statement.setString(2, selectedEvent.getEventDescription());
                statement.setString(3, formatter.format(selectedEvent.getEventDate()));
                statement.setBoolean(4, selectedEvent.getIsVisible());
                int rowsLeft = statement.executeUpdate();
                if (rowsLeft > 0) {
                    errorLabel.setText("");
                    successLabel.setText("Event Saved To The DataBase");
                    refreshText();
                }
                quickUpdate();
                refreshText();
                statement.close();
                connection.close();
            } catch (SQLException event) {
                successLabel.setText("");
                errorLabel.setText("Cannot save this event: " + event.getMessage());
            }
        }
        else {
            successLabel.setText("");
            errorLabel.setText("Cannot save this event, name in use");
        }
    }

    //Changes the publicity of the event non-visible events are only seen by the admins not the public
    // is_active == false means it is not visible
    //Publish Event Button
    public void publishEvent(ActionEvent e)
    {
            sqlCode = ("UPDATE event_list " +
                    "SET is_active = ? " +
                    "WHERE event_name = ?");
            PreparedStatement statement = null;
            try {
                quickUpdate();
                Connection connection = JDBC.getConnection();
                statement = connection.prepareStatement(sqlCode);
                statement.setBoolean(1, true);
                statement.setString(2, selectedEvent.getEventName());
                statement.executeUpdate();
                selectedEvent.setIsVisible(true);
                errorLabel.setText("");
                successLabel.setText("Event Successfully Published");
                refreshText();
                statement.close();
                connection.close();
            } catch (SQLException event) {
                successLabel.setText("");
                errorLabel.setText("Failed to publish event: " + event.getMessage());
            }
    }

    //Edits the currently selected event
    //Edit Event Button
    public void editEvent(ActionEvent e)
    {
        sqlCode = ("UPDATE event_list " +
                "SET event_name = ?, event_description = ?, " +
                "event_date = ?, is_active = ? " +
                "WHERE event_name = ?");
        PreparedStatement statement = null;
        try {
            Connection connection = JDBC.getConnection();
            statement = connection.prepareStatement(sqlCode);
            statement.setString(1,nameTextArea.getText());
            statement.setString(2,descriptionTextArea.getText());
            statement.setString(3,formatter.format(datePicker.getValue()));
            statement.setBoolean(4,selectedEvent.getIsVisible());
            statement.setString(5,selectedEvent.getEventName());
            int rowsLeft = statement.executeUpdate();
            if (rowsLeft > 0) {
                errorLabel.setText("");
                successLabel.setText("Event Edited Successfully");
                quickUpdate();
                refreshText();
            }
            else {
                successLabel.setText("");
                errorLabel.setText("Event could not be found");
            }
            statement.close();
            connection.close();
        } catch (SQLException event) {
            successLabel.setText("");
            errorLabel.setText("Failed to edit event: " + event.getMessage());
        }
    }

    //Deletes the event from the database and removes it from the list of events
    //Delete Event Button
    public void deleteEvent(ActionEvent e)
    {
     sqlCode ="DELETE FROM event_list WHERE event_name = ?";
        PreparedStatement statement = null;
        try {
            Connection connection = JDBC.getConnection();
            statement = connection.prepareStatement(sqlCode);
            statement.setString(1,selectedEvent.getEventName());
            int rowsLeft = statement.executeUpdate();
            if (rowsLeft > 0) {
                successLabel.setText("Event Deleted successfully!");
                errorLabel.setText("");
                selectedEvent = listOfEvents.get(eventIndex);
                listOfEvents.remove(selectedEvent);
                eventIndex--;
                nextEvent();
                refreshText();
            }
            else {
                successLabel.setText("");
                errorLabel.setText("Event could not be found");
            }
            statement.close();
            connection.close();
        } catch (SQLException event) {
            successLabel.setText("");
            errorLabel.setText("Failed to delete event: " + event.getMessage());
        }
    }

    //This will return a boolean to dictate weather the event is already in the DB or not
    //false meaning it's not a valid event name
    //true meaning it's a valid event name
    private boolean validateEventName()
    {
        boolean valid =false;
        sqlCode ="SELECT COUNT(event_id) FROM event_list WHERE event_name = ?";
        PreparedStatement statement = null;

        try {
            Connection connection = JDBC.getConnection();
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
            connection.close();
        } catch (SQLException event) {
            throw new RuntimeException(event);
        }
        return valid;
    }

    //Pulls all the saved event from the DB and reformats the observable list
    //Pull Events Button
    public void pullEvents(ActionEvent e)
    {
        sqlCode ="SELECT event_name, event_description, event_date, is_active FROM event_list";
        PreparedStatement statement = null;

        try {
            Connection connection = JDBC.getConnection();
            statement = connection.prepareStatement(sqlCode);
            ResultSet rs = statement.executeQuery();
            listOfEvents.clear();
            while(rs.next())
            {
                String name = rs.getString("event_name");
                String description = rs.getString("event_description");
                LocalDate date = LocalDate.parse(rs.getString("event_date"));
                Boolean visible =rs.getBoolean("is_active");
                selectedEvent = new Event(name,description,date,visible);
                listOfEvents.add(selectedEvent);
                eventIndex++;
            }
            if (listOfEvents.isEmpty())
            {
                successLabel.setText("");
                errorLabel.setText("No events found in database");
            }
            else {
                eventIndex = listOfEvents.size()-1;
                nextEvent();
                errorLabel.setText("");
                successLabel.setText("Events Pulled From DB");
            }
            rs.close();
            statement.close();
            connection.close();
            refreshText();
        } catch (SQLException event) {
            successLabel.setText("");
            errorLabel.setText("Cannot connect to database: " + event.getMessage());
        }
    }
}