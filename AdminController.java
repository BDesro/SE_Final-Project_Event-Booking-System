
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.w3c.dom.Text;

import javax.swing.*;

public class AdminController {

    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private Label date;
    @FXML
    private TextArea nameTextArea;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextArea dateTextArea;

    private Event selectedEvent;
    public int eventIndex = -1;
    private ObservableList<Event> listOfEvents = FXCollections.observableArrayList();


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
        selectedEvent.setEventDate(dateTextArea.getText());
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


    private void refreshText(){
        date.setText(selectedEvent.getEventDate());
        name.setText(selectedEvent.getEventName());
        description.setText(selectedEvent.getEventDescription());
    }
}