import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.Scene;
public class LoginPage extends Application {

    @Override
    public void start(Stage stage) throws Exception {


        Label usernameLabel = new Label("Username: ");
        TextField username = new TextField();

        Label passwordLabel = new Label("Password: ");
        PasswordField passwordField = new PasswordField();

        Button login = new Button("Login");
        Button createNewAccount = new Button ("Make a new account"); //Case to add to user table with set action

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

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(usernameLabel, username, passwordLabel, passwordField,
                login, createNewAccount);
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Log In Screen");
        stage.setScene(scene);
        stage.show();

    }
    public boolean checkLogin(String username, String password){
        //Default username and password until database is configured
        String validUsername = "user";
        String validPassword = "password123";

        return username.equals(validUsername) && password.equals(validPassword);
    }
    public void clearLogInFields(TextField user, PasswordField pass){
        user.clear();
        pass.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
