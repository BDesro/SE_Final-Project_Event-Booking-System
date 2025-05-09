package edu.westfieldstate.eticketmanager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelpViewController {
    @FXML
    private Label helpLabel;

    public void helpAdmin(){
        helpLabel.setText(
                "Making An Event\n"+
                "--------------------------------------------------------------------------------------------------------------\n"+
                "Click “Create Event” to start a new event. Fill in the following:\n" +
                "Event Name, Description Date (use the calendar picker) Venue (select from the dropdown)" +
                "Click “Save Event” to add it to the database.\n" +
                "Making A Venue\n"+
                "--------------------------------------------------------------------------------------------------------------\n"+
                "Click “Create Venue, Enter:Venue Name and Venue Address, Click “Save Venue”." +
                "Your new venue will appear in the venue dropdown.\n" +
                "Edit An Event\n"+
                "--------------------------------------------------------------------------------------------------------------\n"+
                "Select the event you want to change. Modify the event’s name, description, date, or venue." +
                "Click “Edit” to save the changes.\n"+
                "Publish An Event\n"+
                "--------------------------------------------------------------------------------------------------------------\n"+
                "Click “Publish” to make the selected event visible to the public.\n"+
                "Delete An Event\n"+
                "--------------------------------------------------------------------------------------------------------------\n"+
                "Click “Delete” to permanently remove the selected event.\n"
        );
    }
    public void helpCheckOut(){
        helpLabel.setText("Just Pay Us");
    }
    public void helpLogIn(){
        helpLabel.setText("You Don't Need Help");
    }
    public void helpUser(){
        helpLabel.setText(
                """
                        When you land on this screen:
                        Your Username, Email, Nickname, and Avatar are displayed.
                        A list of events you've purchased tickets for appears on the left.
                        🔒 Note: If you're logged in as a Guest User, profile updates are restricted.
                        
                        Changing Avatar:
                        Use the avatar dropdown (AvatarBox) to pick a new avatar.
                        Click the appropriate Update/Save button (depends on FXML setup).
                        Your avatar will instantly update in the preview.
                        🎨 Tip: Avatars are images chosen from a predefined set.
                        
                        Editing Your NickName:
                        Enter a new nickname in the Nickname box.
                        Optional: Enter a credit card comment (if you like jokes).
                        Click Save/Update Profile.
                        A success message will confirm your profile is saved.
                        ❗ You cannot leave the nickname field empty.
                        
                        Editing Your Credit Card Info:
                        We stole it ok?
                        """
        );
    }
    public void helpGeneral(){
        helpLabel.setText("""
                This page allows you to browse events, view detailed information, and access ticket purchasing options.
                🧑‍💼 User Info
                    
                    Welcome Banner: Displays your username and avatar.
                    
                    Click your username (green hyperlink) to open your profile.
                    
                📅 Event Table (Left Panel)
                    
                    This table lists events by:
                    
                        Event Name
                    
                        Description
                    
                        Date
                    
                    Select a row to view more information about an event in the right panel.
                    
                📍 Venue Filter
                    
                    Use the “Select a Venue” dropdown to filter events by location.
                    
                    Changing the selection automatically updates the event list.
                    
                ℹ️ Event Details (Right Panel)
                    
                When you select an event:
                    
                    Event Name appears in bold at the top.
                    
                    Description, Date, Venue Name, and Venue Address will fill in below.
                    
                    Press “Get Tickets” to proceed to the booking process.
                    
                🔄 Navigation Buttons
                    
                    Back to Login: Return to the login screen.
                    
                    Need Help?: Opens this help box.
                """);
    }
}
