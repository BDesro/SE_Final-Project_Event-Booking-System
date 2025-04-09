
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminController {
    @FXML
    private Event slectedEvent = new Event();
    @FXML
    private Label myLabel;


    public void nameButton(ActionEvent e)
    {
        myLabel.setText(slectedEvent.getEventName());
    }

    public void descriptionButton(ActionEvent e)
    {
        myLabel.setText(slectedEvent.getEventDescription());
    }


    public void dateButton(ActionEvent e)
    {
        myLabel.setText(slectedEvent.getEventDate());
    }

}