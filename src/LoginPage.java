import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// No longer extends Application, since it is plugged into SceneManager.switchTo()
public class LoginPage {

    // Creates and returns the root needed to set up the screen (in SceneManager)
    // All functionality except for stage and scene remains the same
    public static Parent getRootNode() { //A second class should be made to handle log in methods and database queries
        //Want this class to focus on the login screen layout, will do with next branch

        Label usernameLabel = new Label("Username: ");
        TextField username = new TextField();

        Label passwordLabel = new Label("Password: ");
        PasswordField passwordField = new PasswordField();

        Button login = new Button("Login");
        Button createNewAccount = new Button ("Make a new account"); //Case to add to user table with set action

        Button tempAdmin = new Button("Temp goto Admin Screen");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(usernameLabel, username, passwordLabel, passwordField,
                login, createNewAccount);

        login.setOnAction(e ->{
            String user = username.getText();
            String password = passwordField.getText();

            if(checkLogin(user, password)) {
                System.out.println("Log in successful!"); //Don't have general user screen yet
                username.clear();
                passwordField.clear();
            }
            else
                System.out.println("Incorrect password or username. Try again");

        });
        createNewAccount.setOnAction(e -> {
            // Temporary dialog (can be made into a new Scene if needed)
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Create Account");
            dialog.setHeaderText("Enter your details (username,email,password)");

            Label newUserLabel = new Label("New Username:");
            TextField newUserField = new TextField();
            Label newEmailLabel = new Label("New Email:");
            TextField newEmailField = new TextField();
            Label newPassLabel = new Label("New Password:");
            PasswordField newPassField = new PasswordField();
            Button submitNewAccount = new Button("Submit New Account");

            submitNewAccount.setOnAction(submitEvent -> {
                String newUsername = newUserField.getText().trim();
                String newEmail = newEmailField.getText().trim();
                String newPassword = newPassField.getText();

                if (newUsername.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
                    System.out.println("Please fill out all fields.");
                    return;
                }

                if (emailHasAccount(newEmail)) {
                    System.out.println("Email is already in use.");
                } else {
                    User newUser = new User(newUsername, newPassword, newEmail, User.Role.USER); //Default as user account
                    if (createAccount(newUser)) {
                        System.out.println("Account created successfully!");
                        newUserField.clear();
                        newEmailField.clear();
                        newPassField.clear();
                    } else {
                        System.out.println("Account creation failed.");
                    }
                }
            });
            root.getChildren().addAll(
                    newUserLabel, newUserField,
                    newEmailLabel, newEmailField,
                    newPassLabel, newPassField,
                    submitNewAccount
            ); //Should make method or code to take out fields and texts after account is created
        });

        tempAdmin.setOnAction(e -> SceneManager.switchTo(SceneID.ADMIN_SCREEN));
        root.getChildren().addAll(tempAdmin);
        return root;
    }
    public static boolean checkLogin(String username, String password){
        /* This will only work properly once passwords are properly
            hashed during account creation using
                PasswordUtils.hashPassword("the password goes here");

           For now, use "Guest User" for username and "N/A" for password
           to test login functionality (remember to update locally to
                                        current db creation script)
         */
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

    public static boolean emailHasAccount(String email){ //Method to make sure each email only has one account
        String query = "SELECT COUNT(*) FROM users WHERE email_address = ?";
        try (Connection connection = JDBC.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean createAccount(User user) { //Creating account method
        String query = "INSERT INTO users (username, password_hash, email_address, user_role) VALUES (?, ?, ?, ?)";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getHashedPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole().toString().toLowerCase());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
