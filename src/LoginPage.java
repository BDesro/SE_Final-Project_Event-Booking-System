import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;

// No longer extends Application, since it is plugged into SceneManager.switchTo()
public class LoginPage {

    // Creates and returns the root needed to set up the screen (in SceneManager)
    // All functionality except for stage and scene remains the same
    public static Parent getRootNode() {


        Label usernameLabel = new Label("Username: ");
        TextField username = new TextField();

        Label passwordLabel = new Label("Password: ");
        PasswordField passwordField = new PasswordField();

        Button login = new Button("Login");
        Button createNewAccount = new Button ("Make a new account"); //Case to add to user table with set action

        Button tempAdmin = new Button("Temp goto Admin Screen");

        login.setOnAction(e ->{
            String user = username.getText();
            String password = passwordField.getText();

            if(checkLogin(user, password)) {
                System.out.println("Log in successful!");
                clearLogInFields(username, passwordField);
            }
            else
                System.out.println("Incorrect password or username. Try again");

        });

        tempAdmin.setOnAction(e -> SceneManager.switchTo(SceneID.ADMIN_SCREEN));

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        root.getChildren().addAll(usernameLabel, username, passwordLabel, passwordField,
                login, createNewAccount, tempAdmin);
        return root;
    }
    public static boolean checkLogin(String username, String password){
        //Default username and password until database is configured
        String validUsername = "user";
        String validPassword = "password123";

        return username.equals(validUsername) && password.equals(validPassword);
    }
    public static void clearLogInFields(TextField user, PasswordField pass){
        user.clear();
        pass.clear();
    }
}
