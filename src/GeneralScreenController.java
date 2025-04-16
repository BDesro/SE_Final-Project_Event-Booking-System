import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public void initializeEventTable()
    {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

        ObservableList<Event> events = Event.getAllEvents();
        table.setItems(events);

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
                    // Can use this to trigger seating screen, etc.
                }
            });
            return row;
        });
    }
}
