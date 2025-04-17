import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainSeatingController { //This class is going to handle the mutliple seats, rows, and labels for the
    //seating screen

    @FXML
    private GridPane seatGrid;

    @FXML
    public void initialize() {
        try {
            int rows = 3;
            int cols = 5;

            //Setting up seat labels with default 3 and 5 for now
            for (int row = 0; row < rows; row++) {
                char rowLetter = (char) ('A' + row);

                for (int col = 1; col <= cols; col++) {
                    String seatId = rowLetter + String.valueOf(col);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("seat.fxml"));
                    VBox seat = loader.load();

                    SeatController controller = loader.getController();
                    controller.setSeatLabel(seatId);

                    seatGrid.add(seat, col - 1, row);
                    GridPane.setMargin(seat, new Insets(10));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
