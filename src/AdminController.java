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
    private JDBC jdbc = new JDBC();
    private String sqlCode;


    public void createEvent(ActionEvent e)
    {
        selectedEvent = new Event();
        listOfEvents.add(selectedEvent);
        refreshText();
        eventIndex++;
    }

    public void nextEvent()
    {
        if(eventIndex + 1 == listOfEvents.size())
            eventIndex = 0;
        else
            eventIndex++;
        selectedEvent = listOfEvents.get(eventIndex);
        refreshText();
    }


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

    private void quickUpdate()
    {
        selectedEvent.setEventName(nameTextArea.getText());
        selectedEvent.setEventDescription(descriptionTextArea.getText());
        selectedEvent.setEventDate(datePicker.getValue());
    }

    // Temp function to go back to login screen for easy viewing/testing
    public void goToLogin(ActionEvent e){
        SceneManager.switchTo(SceneID.LOGIN_SCREEN);
    }
    // ==============================================================================================
    //                                    DATA BASE CONNECTION
    // ==============================================================================================

    public void saveEvent(ActionEvent e)
    {
        if(validateEvent()) {
            sqlCode = ("INSERT INTO event_list (event_name, event_description, " +
                    "event_date, is_active) VALUES (?,?,?,?)");
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
                    successLabel.setText("Event Saved To The DataBase");
                    errorLabel.setText("");
                    refreshText();
                }
                quickUpdate();
                refreshText();
                statement.close();
                connection.close();
            } catch (SQLException event) {
                successLabel.setText("");
                errorLabel.setText("Cannot Save This Event");
                throw new RuntimeException(event);
            }
        }
    }
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
            statement.setBoolean(1,true);
            statement.setString(2,selectedEvent.getEventName());
            statement.executeUpdate();
            selectedEvent.setIsVisible(true);
            errorLabel.setText("");
            successLabel.setText("Event Successfully Published");
            refreshText();
            statement.close();
            connection.close();
        }
        catch (SQLException event) {
            throw new RuntimeException(event);
            //event.printStackTrace();
            //errorLabel.setText("Database error: " + event.getMessage());
        }
    }

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
                successLabel.setText("Event Edited Successfully");
                errorLabel.setText("");
                quickUpdate();
                refreshText();
            }
            else {
                errorLabel.setText("Event Could Not Be Found");
                successLabel.setText("");
            }

            statement.close();
            connection.close();
        } catch (SQLException event) {
            throw new RuntimeException(event);
        }
    }
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
                errorLabel.setText("Event Could Not Be Found");
                successLabel.setText("");
            }
            statement.close();
            connection.close();
        } catch (SQLException event) {
            throw new RuntimeException(event);
        }
    }

    private boolean validateEvent()
    {
        boolean valid =false;
        sqlCode ="SELECT COUNT(event_id) FROM event_list WHERE event_name = ?";
        PreparedStatement statement = null;

        try {
            Connection connection = JDBC.getConnection();
            statement = connection.prepareStatement(sqlCode);
            statement.setString(1,nameTextArea.getText());
            ResultSet rs = statement.executeQuery();
            if(rs.next())
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
                errorLabel.setText("");
                successLabel.setText("Events Pulled From DB");
            }
            eventIndex = listOfEvents.size()-1;
            nextEvent();
            rs.close();
            statement.close();
            connection.close();
            refreshText();
        } catch (SQLException event) {
            errorLabel.setText("Cannot Connect To DB");
            successLabel.setText("");
            throw new RuntimeException(event);
        }
    }
}