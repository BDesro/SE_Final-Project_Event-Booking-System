import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
public class SeatController { //This class is setting up the shape each individual seat will look like and how
    //they each should interact with the user mouse and clicking
    @FXML private VBox seatPane;
    @FXML private Rectangle base;
    @FXML private Rectangle backrest;
    private String seatId = "Seat";

    @FXML
    private javafx.scene.text.Text seatLabel;

    private boolean isReserved = false;
    private Tooltip tooltip;

    @FXML
    public void initialize() {
        tooltip = new Tooltip("Seat - Available"); //Tooltip (check on Oracle) is a tool that shows additional info
        //When you hover over the object you install it to
        //Can be modified to show pricing for later
        Tooltip.install(seatPane, tooltip);
    }

    @FXML
    private void handleClick(MouseEvent event) { //Event for future refactoring where if the user clicks on a seat
        //it will change color to represent reserved or available
        //Will also change what it shows when you hover over it from seat id (available or reserved)
        isReserved = !isReserved;

        Color newColor;
        if (isReserved) {
            newColor = Color.SALMON;
        } else {
            newColor = Color.LIGHTGREEN;
        }
        base.setFill(newColor);
        backrest.setFill(newColor);

        if (isReserved) {
            tooltip.setText("Seat - Reserved");
        } else {
            tooltip.setText("Seat - Available");
        }
    }

    //Initial label for when user opens screen
    public void setSeatLabel(String label) {
        this.seatId = label;
        seatLabel.setText(label);
        tooltip.setText(seatId + " - Available");
    }
}
