import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private Event selectedEvent;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public int eventIndex = -1;
    private ObservableList<Event> listOfEvents = FXCollections.observableArrayList();
    private JDBC jdbc = new JDBC();


    public void createEvent(ActionEvent e)
    {
        selectedEvent = new Event();
        refreshText();
        listOfEvents.add(selectedEvent);
        eventIndex++;
    }

    public void editName(ActionEvent e) {
        selectedEvent.setEventName(nameTextArea.getText());
        refreshText();}

    public void editDescription(ActionEvent e) {
        selectedEvent.setEventDescription(descriptionTextArea.getText());
        refreshText();}

    public void editDate(ActionEvent e) {
        selectedEvent.setEventDate(datePicker.getValue());
        refreshText();}

    public void nextEvent(ActionEvent e)
    {
        if(eventIndex + 1 == listOfEvents.size())
            eventIndex = 0;
        else
            eventIndex++;
        selectedEvent = listOfEvents.get(eventIndex);
        refreshText();
    }

    public void makePublic(ActionEvent e)
    {
            publicity.setText("It's public");
            selectedEvent.setIsVisible(true);
    }

    private void refreshText(){
        date.setText(formatter.format(selectedEvent.getEventDate()));
        name.setText(selectedEvent.getEventName());
        description.setText(selectedEvent.getEventDescription());
        if (selectedEvent.getIsVisible())
            publicity.setText("It's public");
        else publicity.setText("It's not public");
    }

   // ==============================================================================================
   //                                    PREPARED STATEMENTS
   // ==============================================================================================
    private String publishPrepStatement()
    {
        String sqlCode = ("INSERT INTO event_list (event_name, event_description, " +
                "event_date, is_active) VALUES ('"
                + selectedEvent.getEventName() + "', '"
                + selectedEvent.getEventDescription() + "', '"
                + formatter.format(selectedEvent.getEventDate()) + "', '"
                + selectedEvent.getIsVisible() + "');");
        return sqlCode;
    }
    private String editPrepStatement()
    {
        String sqlCode = ("UPDATE event_list " +
                "SET event_name = '" + nameTextArea.getText() + "', "+
                "event_description = '" + descriptionTextArea.getText() +"', "+
                "event_date = '" + formatter.format(selectedEvent.getEventDate()) + "'," +
                "is_active = '" + selectedEvent.getIsVisible() + "' "+
                "WHERE event_name = '" + selectedEvent.getEventName() + "';");
        return sqlCode;
    }
    private String deletePrepStatement()
    {
        String sqlCode ="DELETE FROM event_list WHERE event_name = '" + selectedEvent.getEventName() + "';";
                //DELETE FROM table_name WHERE condition LIMIT 1;
        return sqlCode;
    }

    // ==============================================================================================
    //                                    DATA BASE CONNECTION
    // ==============================================================================================
    private void publishEvent()
    {
       // selectedEvent.setIsVisible(true);
        Connection connection = JDBC.getConnection();
        PreparedStatement statement = connection.prepareStatement(publishPrepStatement());
    }
    private void editEvent()
    {
        Connection connection = JDBC.getConnection();
        PreparedStatement statement = connection.prepareStatement(editPrepStatement());
    }
    private void deleteEvent()
    {
        Connection connection = JDBC.getConnection();
        PreparedStatement statement = connection.prepareStatement(deletePrepStatement());
    }

}