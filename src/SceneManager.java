import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager
{
    private static Stage stage;

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
            Make sure to add an id for each scene in the SceneID enum file,
            then update the switch statement in this method so that it can
            be accessed.

          Pure Java:  new Scene(*yourFileName*.getRootNode(), 500, 500);

                Rather than creating your scenes to extend Application,
                have your class declared normally and create a method
                getRootNode() that does the same thing as if the scene
                were independent, but returns its root so the scene
                manager can handle the scene switching/assignment

                Ex:
                    public class LoginPage {
                        public static Parent getRootNode()...


          FXML:       new Scene(loadFXML("Your FXML File Name"), 500, 500);
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
            switch(id)
            {
                case LOGIN_SCREEN:
                    scene = new Scene(LoginPage.getRootNode(), 500, 500);
                    stage.setTitle("Login Screen");
                    break;
                case ADMIN_SCREEN:
                    scene = new Scene(loadFXML("admin-view.fxml"), 500, 500);
                    stage.setTitle("Admin Screen");
                    break;
            }

            if(scene != null)
            {
                stage.setScene(scene);
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
