import javafx.application.Application;
import javafx.stage.Stage;

/*
    This class serves as the central entry point to the application.

    Using the SceneBuilder class's methods,
*/
public class MainApplication extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        SceneManager.setStage(stage);
        SceneManager.switchTo(SceneID.LOGIN_SCREEN); // Starts out at login, scenes change
                                                     // through action event triggers from there
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
