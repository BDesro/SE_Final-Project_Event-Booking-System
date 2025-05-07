package edu.westfieldstate.eticketmanager.controller;

import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.model.Event;
import edu.westfieldstate.eticketmanager.model.User;
import edu.westfieldstate.eticketmanager.model.Venue;
import edu.westfieldstate.eticketmanager.resources.Avatar;
import edu.westfieldstate.eticketmanager.resources.AvatarManager;
import edu.westfieldstate.eticketmanager.util.GifUtil;
import edu.westfieldstate.eticketmanager.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


import java.sql.*;
import java.time.LocalDate;

public class GeneralScreenController
{
    private User activeUser;
    @FXML private ImageView avatarIMG;
    @FXML private Hyperlink nickNameLabel;

    @FXML private TableView<Event> table;
    @FXML private TableColumn<Event, String> nameColumn;
    @FXML private TableColumn<Event, String> descriptionColumn;
    @FXML private TableColumn<Event, LocalDate> dateColumn;
    @FXML private ComboBox<Venue> venueSelector;

    @FXML private ImageView gifView;

    private ObservableList<Event> tableEvents;

    @FXML private Rectangle eventDetailsBackground;
    @FXML private VBox eventDetailsVBox;
    @FXML private Label eventNameLabel;
    @FXML private Label eventDescriptionLabel;
    @FXML private Label eventDateLabel;
    @FXML private Label venueNameLabel;
    @FXML private Label venueAddressLabel;

    @FXML
    public void initialize()
    {
        getActiveUser();
        nickNameLabel.setText(activeUser.getNickName());
        avatarIMG.setImage(new Image(AvatarManager.getAvatarLocation(activeUser.getAvatar()),true));

        initializeVenueSelector();
        tableEvents = FXCollections.observableArrayList();
        initializeTableView();

        setEventDetailsVisibility(false);

        //This is here temporarily to show that the API works, it will go in the checkout screen
        //for when a purchase is complete.
        GifUtil.loadRandomGifIntoImageView(gifView);
    }

    public void getActiveUser()
    {
        String query = "SELECT * FROM users WHERE is_active = 1";

        try(Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                String username = rs.getString("username");
                String email = rs.getString("email_address");
                String nickname = rs.getString("nickname");
                Avatar avatar = Avatar.valueOf(rs.getString("avatar"));
                activeUser = new User(username, email,nickname,avatar); //Will add feature to get avatar, harder than I thought
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void backToLogin(ActionEvent e){
        String query = "UPDATE users " +
                "SET is_active = 0 " +
                "WHERE is_active = 1";

        try(Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }

        SceneManager.switchTo(SceneID.LOGIN_SCREEN);
    }

    private void initializeVenueSelector()
    {
        String query ="SELECT venue_name, address " +
                      "FROM venues";

        if(!venueSelector.getItems().isEmpty())
            venueSelector.getItems().clear();

        try(Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                venueSelector.getItems().add(new Venue(rs.getString("venue_name"), rs.getString("address")));
            }
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeTableView()
    {
        table.setPlaceholder(new Label("No Venue Selected"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

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

                    eventNameLabel.setText(selected.getEventName());
                    eventDescriptionLabel.setText(selected.getEventDescription());
                    eventDateLabel.setText(String.valueOf(selected.getEventDate()));
                    venueNameLabel.setText(selected.getEventVenue().getVenueName());
                    venueAddressLabel.setText(selected.getEventVenue().getVenueLocation());

                    if(!eventDetailsVBox.isVisible())
                        setEventDetailsVisibility(true);
                    // Can use this to trigger seating screen, etc. later
                }
            });
            return row;
        });
    }

    public void pullPublicVenueEvents(Venue venue)
    {
        tableEvents.clear();

        if (venue == null)
        {
            table.setPlaceholder(new Label("No Venue Selected"));
            return;
        }

        String query = "SELECT * FROM event_venue " +
                       "WHERE is_active = 1 && venue_name = ? " +
                       "ORDER BY event_date";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setString(1, venue.getVenueName());
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                String eventName = rs.getString("event_name");
                String eventDescription = rs.getString("event_description");
                LocalDate date = LocalDate.parse(rs.getString("event_date"));
                Boolean active = rs.getBoolean("is_active");
                String venueName = rs.getString("venue_name");
                String address = rs.getString("address");
                tableEvents.add(new Event(eventName, eventDescription, date, active, new Venue(venueName, address)));
            }
            rs.close();

            if(tableEvents.isEmpty())
                table.setPlaceholder(new Label("No Events Available for this Venue"));

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private void setEventDetailsVisibility(boolean visibility)
    {
        eventDetailsBackground.setVisible(visibility);
        eventDetailsVBox.setVisible(visibility);
    }

    @FXML
    public void updateTable(ActionEvent e)
    {
        pullPublicVenueEvents(venueSelector.getSelectionModel().getSelectedItem());
        table.setItems(tableEvents);

        setEventDetailsVisibility(false);
    }

    public void openProfile()
    {
        SceneManager.switchTo(SceneID.USER_PROFILE);
    }

}
