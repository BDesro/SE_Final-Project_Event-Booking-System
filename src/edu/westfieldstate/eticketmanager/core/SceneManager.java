package edu.westfieldstate.eticketmanager.core;

import edu.westfieldstate.eticketmanager.controller.CreateAccountScreen;
import edu.westfieldstate.eticketmanager.controller.LoginPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager
{
    private static Stage stage;
    private static int resX = 1280;
    private static int resY = 720;

    public static void setStage(Stage s)
    {
        stage = s;
    }

    /*
        This method allows you to simply enter a scene id and switches
        the current scene to that scene. This can be done regardless
        of whether you used FXML/SceneBuilder or pure Java code for
        your scene.

         ******************************************************************
            Make sure to add an id for each scene in the edu.westfieldstate.eticketmanager.core.SceneID enum file,
            then update the switch statement in this method so that it can
            be accessed.

          Pure Java:  new Scene(*yourFileName*.getRootNode(), 500, 500);

          FXML:       new Scene(loadFXML("Your FXML File Path"), 500, 500);
         ******************************************************************
    */
    public static void switchTo(SceneID id)
    {
        Scene scene = null;

        try
        {
            // Update this with a new case block when you add a new scene
            // (and new enum id)

            // Declare the Scene based on the parameters above for FXML
            //  vs pure Java
            switch (id)
            {
                case LOGIN_SCREEN ->
                {
                    scene = new Scene(LoginPage.getRootNode(), 500, 400);
                    stage.setTitle("Login Screen");
                }
                case ADMIN_SCREEN ->
                {
                    scene = new Scene(loadFXML("/edu/westfieldstate/eticketmanager/view/admin-view.fxml"), resX, resY);
                    stage.setTitle("Admin Screen");
                }
                case GENERAL_SCREEN ->
                {
                    scene = new Scene(loadFXML("/edu/westfieldstate/eticketmanager/view/general-view.fxml"), resX, resY);
                    stage.setTitle("General User Screen");
                }
                case CREATE_SCREEN ->
                {
                    scene = new Scene(CreateAccountScreen.getRootNode(), 550, 300);
                    stage.setTitle("Create Account Screen");
                }
                case USER_PROFILE ->
                {
                    scene = new Scene(loadFXML("/edu/westfieldstate/eticketmanager/view/profile-view.fxml"), resX, resY);
                    stage.setTitle("User Profile");
                }
                case SQL_PASSWORD ->
                {
                    scene = new Scene(loadFXML("/edu/westfieldstate/eticketmanager/view/sql-view.fxml"), resX, resY);
                    stage.setTitle("Enter Your SQL Password");
                }
                case PURCHASE_DONE ->
                {
                    scene = new Scene(loadFXML("/edu/westfieldstate/eticketmanager/view/purchase_confirmed.fxml"), resX, resY);
                    stage.setTitle("Complete Purchase!");
                }
                case CHECKOUT ->
                {
                    scene = new Scene(loadFXML("/edu/westfieldstate/eticketmanager/view/checkout_screen.fxml"), 600, 290);
                    stage.setTitle("Check Out");
                }
                case SEAT_CHART ->{
                    scene = new Scene(loadFXML("/edu/westfieldstate/eticketmanager/view/main-seating.fxml"), 1050, 1000);
                    stage.setTitle("Seating Chart");
                }
            }

            if(scene != null)
            {
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String path) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(path));
        return loader.load();
    }
}
