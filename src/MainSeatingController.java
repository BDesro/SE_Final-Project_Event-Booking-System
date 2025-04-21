import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainSeatingController { //This class is going to handle the mutliple seats, rows, and labels for the
    //seating screen

    @FXML
    private GridPane seatGrid;

    @FXML
    public void initialize() {
        try {
            SeatController seatController = new SeatController();
            for (Seat seat : seatController.getAllSeats()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("seat.fxml"));
                VBox seatNode = loader.load();

                SeatController controller = loader.getController();
                controller.setSeat(seat);

                //Should make seatRow and section return a char
                int rowIndex = Character.toUpperCase(seat.getSeatSection().charAt(0)) - 'A';
                int colIndex = seat.getSeatNum() - 1;

                seatGrid.add(seatNode, colIndex, rowIndex);
                GridPane.setMargin(seatNode, new Insets(10));
            }
        } catch (Exception e) {
            System.out.println("Error loading seats from database");
        }
    }

}
