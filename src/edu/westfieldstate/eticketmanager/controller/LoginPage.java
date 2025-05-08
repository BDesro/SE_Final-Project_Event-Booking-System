package edu.westfieldstate.eticketmanager.controller;

import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.model.User;
import edu.westfieldstate.eticketmanager.util.JDBC;
import edu.westfieldstate.eticketmanager.util.PasswordUtils;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage {

    // Creates and returns the root needed to set up the screen (in edu.westfieldstate.eticketmanager.core.SceneManager)
    // All functionality except for stage and scene remains the same

    private static String userRole = "";
    public static Parent getRootNode() { //A second class should be made to handle log in methods and database queries
        //Want this class to focus on the login screen layout, will do with next branch

        Label usernameLabel = new Label("Username: ");
        TextField username = new TextField();

        Label passwordLabel = new Label("Password: ");
        PasswordField password = new PasswordField();
        Label message = new Label("");

        Button login = new Button("Login");
        Button createNewAccount = new Button ("Make a new account"); //Case to add to user table with set action
        Button help = new Button("Help");
        Button guestLogIn = new Button("Log In As Guest");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(usernameLabel, username, passwordLabel, password,
                login, guestLogIn, createNewAccount, help);
        //Sets the background to a dark greay
        root.setStyle("-fx-background-color: #121212;");

        //Labels are white
        usernameLabel.setStyle("-fx-text-fill: white;");
        passwordLabel.setStyle("-fx-text-fill: white;");

        //Text in texftfield is what, darker greay border color and lighter greay backgrounf
        username.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; -fx-border-color: #333;");
        password.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; -fx-border-color: #333;");

        //Color for buttons, background is spotify green with black text and bold font
        login.setStyle("-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-weight: bold;");
        guestLogIn.setStyle("-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-weight: bold;");
        createNewAccount.setStyle("-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-weight: bold;");
        help.setStyle("-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-weight: bold;");
        help.setOnAction(e ->{
            AdminController controller = new AdminController();
            controller.helpBox();
        });

        login.setOnAction(e ->{
            String user = username.getText();
            String pass = password.getText();

            if(checkLogin(user, pass)) {
                message.setText("Log In Success!!");
                message.setStyle("-fx-text-fill: green;");
                username.clear();
                password.clear();

                setActiveUser(user);
                if(userRole.equals("user"))
                    SceneManager.switchTo(SceneID.GENERAL_SCREEN);
                else if(userRole.equals("admin"));
                    SceneManager.switchTo(SceneID.ADMIN_SCREEN);
            }
            else {
                message.setText("Incorrect Username or password. Please try again");
                message.setStyle("-fx-text-fill: red;");
                username.clear();
                password.clear();
            }

        });
        guestLogIn.setOnAction(e-> {
            setActiveUser("Guest User");
            SceneManager.switchTo(SceneID.GENERAL_SCREEN);
        });
        createNewAccount.setOnAction(e -> SceneManager.switchTo(SceneID.CREATE_SCREEN));
        
        root.getChildren().addAll(message);
        return root;
    }
    public static boolean checkLogin(String username, String password){
        String code = "SELECT password_hash, user_role FROM users WHERE username = ?";

        try (Connection connect = JDBC.getConnection();
             PreparedStatement ps = connect.prepareStatement(code))
        {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                String storedHashPassword = rs.getString("password_hash");
                userRole = rs.getString("user_role");
                return PasswordUtils.checkPassword(password, storedHashPassword);
            } else {
                return false;
            }
        } catch(SQLException e){
            System.out.println("Error with retrieving database!");
            return false;
        }

        //return username.equals(validUsername) && password.equals(validPassword);
    }

    public static boolean setActiveUser(String username)
    {
        String query = "UPDATE users " +
                       "SET is_active = 1 " +
                       "WHERE username = ?";

        try(Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setString(1, username);

            ps.executeUpdate();
            return true;
        } catch(SQLException e) {
            System.out.println("Error with updating database!");
            return false;
        }
    }
}
