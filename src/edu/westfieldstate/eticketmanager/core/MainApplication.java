package edu.westfieldstate.eticketmanager.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/*
    This class serves as the central entry point to the application
    using the SceneBuilder class's methods.
*/
public class MainApplication extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        //KML testing seating layout and screen, don't worry about this right now will be changed when seating screen
        //is made with SCENE ID (gotta remember)
/*
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-seating.fxml")));
        stage.setTitle("Seating Chart");
        stage.setScene(new Scene(root, 500, 300));
        stage.show();

 */
        SceneManager.setStage(stage);
        SceneManager.switchTo(SceneID.SQL_PASSWORD); // Starts out at login, scenes change
                                                     // through action event triggers from there
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
