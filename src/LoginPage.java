import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage {

    public static User activeUser = null;

    // Creates and returns the root needed to set up the screen (in SceneManager)
    // All functionality except for stage and scene remains the same
    public static Parent getRootNode() { //A second class should be made to handle log in methods and database queries
        //Want this class to focus on the login screen layout, will do with next branch

        Label usernameLabel = new Label("Username: ");
        TextField username = new TextField();

        Label passwordLabel = new Label("Password: ");
        PasswordField passwordField = new PasswordField();
        Label messageLabel = new Label("");

        Button login = new Button("Login");
        Button createNewAccount = new Button ("Make a new account"); //Case to add to user table with set action

        Button tempAdmin = new Button("Temp goto Admin Screen");
        Button guestLogIn = new Button("Log In As Guest");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(usernameLabel, username, passwordLabel, passwordField,
                login, guestLogIn, createNewAccount);

        login.setOnAction(e ->{
            String user = username.getText();
            String password = passwordField.getText();

            if(checkLogin(user, password)) {
                messageLabel.setText("Log In Success!!");
                messageLabel.setStyle("-fx-text-fill: green;");
                username.clear();
                passwordField.clear();

                SceneManager.switchTo(SceneID.GENERAL_SCREEN);
            }
            else {
                messageLabel.setText("Incorrect Username or password. Please try again");
                messageLabel.setStyle("-fx-text-fill: red;");
                username.clear();
                passwordField.clear();
            }

        });
        guestLogIn.setOnAction(e->SceneManager.switchTo(SceneID.GENERAL_SCREEN));
        createNewAccount.setOnAction(e ->SceneManager.switchTo(SceneID.CREATE_SCREEN));

        tempAdmin.setOnAction(e -> SceneManager.switchTo(SceneID.ADMIN_SCREEN));
        root.getChildren().addAll(tempAdmin, messageLabel);
        return root;
    }
    public static boolean checkLogin(String username, String password){
        String query = "SELECT password_hash FROM users WHERE username = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                String storedHashPassword = rs.getString("password_hash");
                return PasswordUtils.checkPassword(password, storedHashPassword);
            } else {
                return false;
            }
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }

        //return username.equals(validUsername) && password.equals(validPassword);
    }



}
