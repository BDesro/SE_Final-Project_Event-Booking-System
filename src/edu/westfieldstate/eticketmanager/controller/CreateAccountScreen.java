package edu.westfieldstate.eticketmanager.controller;

import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.model.User;
import edu.westfieldstate.eticketmanager.resources.Avatar;
import edu.westfieldstate.eticketmanager.util.JDBC;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAccountScreen {

    public static Parent getRootNode() {
        Label message = new Label("");

        Label newUserLabel = new Label("New Username:");
        TextField newUser = new TextField();
        Label newEmailLabel = new Label("New Email:");
        TextField newEmail = new TextField();
        Label newPassLabel = new Label("New Password:");
        PasswordField newPass = new PasswordField();
        Label passMessage = new Label("Must be 8+ characters long with 1 uppercase, special character, and number");
        passMessage.setStyle("-fx-text-fill: red;");


        ProgressBar strengthBar = new ProgressBar(0);
        strengthBar.setPrefWidth(200);
        strengthBar.setStyle("-fx-accent: red;");
        Label strengthLabel = new Label("Strength: ");
        strengthLabel.setStyle("-fx-text-fill: red;");

        strengthBar.setVisible(false);
        strengthBar.setManaged(false);
        strengthLabel.setVisible(false);
        strengthLabel.setManaged(false);
        passMessage.setVisible(false);
        passMessage.setManaged(false);

        Button returnToLogIn = new Button ("Back to Log In");
        Button submitNewAccount = new Button("Submit New Account");

        newPass.textProperty().addListener((observed, old, newValue) -> {
            double strength = calculateStrength(newValue);
            strengthBar.setProgress(strength);
            if (strength < 0.4) {
                strengthBar.setStyle("-fx-accent: red;");
                strengthLabel.setText("Strength: Weak");
                strengthLabel.setStyle("-fx-text-fill: red;");
            } else if (strength < 0.7) {
                strengthBar.setStyle("-fx-accent: orange;");
                strengthLabel.setText("Strength: Medium");
                strengthLabel.setStyle("-fx-text-fill: orange;");
            }
            if (!passwordCheck(newValue)) {
                passMessage.setText("Must be 8+ characters long with 1 uppercase, special character, and number");
                passMessage.setStyle("-fx-text-fill: red;");
            } else {
                strengthBar.setStyle("-fx-accent: green;");
                strengthLabel.setText("Strength: Strong password");
                strengthLabel.setStyle("-fx-text-fill: green;");
                passMessage.setText("Password requirements met!");
                passMessage.setStyle("-fx-text-fill: green;");
            }
        });

        newPass.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            strengthBar.setVisible(isFocused);
            strengthBar.setManaged(isFocused);
            strengthLabel.setVisible(isFocused);
            strengthLabel.setManaged(isFocused);
            passMessage.setVisible(isFocused);
            passMessage.setManaged(isFocused);
        });

        submitNewAccount.setOnAction(submitEvent -> {
            String newUsername = newUser.getText().trim();
            String newEmails = newEmail.getText().trim();
            String newPassword = newPass.getText();

            if (newUsername.isEmpty() || newEmails.isEmpty() || newPassword.isEmpty()) {
                message.setText("All fields are required.");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            if (!passwordCheck(newPassword)) {
                message.setText("Password does not meet requirements");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            if (emailHasAccount(newEmails)) {
                message.setText("Email already has associated account!!!!");
                message.setStyle("-fx-text-fill: red;");
                newEmail.clear();
            } else {
                Avatar avatar = Avatar.Default;
                User user = new User(newUsername, newPassword, newEmails, User.Role.USER, avatar); //Default as user account
                if (createAccount(user)) {
                    message.setText("Account created successfully!");
                    message.setStyle("-fx-text-fill: green;");
                    newUser.clear();
                    newEmail.clear();
                    newPass.clear();
                } else {
                    message.setText("Error in making the account");
                    message.setStyle("-fx-text-fill: red;");
                }
            }
        });
        returnToLogIn.setOnAction(e ->
            SceneManager.switchTo(SceneID.LOGIN_SCREEN));
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        HBox userRow = new HBox(10, newUserLabel, newUser);
        HBox emailRow = new HBox(10, newEmailLabel, newEmail);
        VBox passRow = new VBox(5, new HBox(10, newPassLabel, newPass), passMessage, strengthBar, strengthLabel);
        HBox buttonRow = new HBox(10, submitNewAccount, returnToLogIn);
        root.getChildren().addAll(userRow, emailRow, passRow, buttonRow, message);
        root.setStyle("-fx-background-color: #121212;");

        newUserLabel.setStyle("-fx-text-fill: white;");
        newEmailLabel.setStyle("-fx-text-fill: white;");
        newPassLabel.setStyle("-fx-text-fill: white;");
        strengthLabel.setStyle("-fx-text-fill: white;");
        newUser.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; -fx-border-color: #333;");
        newEmail.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; -fx-border-color: #333;");
        newPass.setStyle("-fx-background-color: #1e1e1e; -fx-text-fill: white; -fx-border-color: #333;");
        submitNewAccount.setStyle("-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-weight: bold;");
        returnToLogIn.setStyle("-fx-background-color: #1DB954; -fx-text-fill: black; -fx-font-weight: bold;");
        strengthBar.setStyle("-fx-background-color: #1DB954;");
        
        return root;
    }
    public static boolean emailHasAccount(String email){ //Method to make sure each email only has one account
        String codeLine = "SELECT COUNT(*) FROM users WHERE email_address = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(codeLine)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error in executing the query!");
        }

        return false;
    }

    public static boolean passwordCheck(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasNumber = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }

        return hasUpper && hasNumber;
    }

    private static double calculateStrength(String password) {
        int score = 0;

        if (password.length() >= 8) score++; //Score increases a point if string is 8 characters or more
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) { //Increases if ther is upper case too
                score++;
                break;
            }
        }
        for (int i = 0; i < password.length(); i++) { //Increases when we find a digit
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                score++;
                break;
            }

        }
        for (int i = 0; i < password.length(); i++) {//Increase for special characters
            char c = password.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                score++;
                break;
            }
        }

        return Math.min(score / 4.0, 1.0); //If score gets all 4 points it is strong, anything less is weak or medium
    }

    public static boolean createAccount(User user){ //Creating account method
        String line = "INSERT INTO users (username, password_hash, email_address, user_role) VALUES (?, ?, ?, ?)";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement ps = connection.prepareStatement(line)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getHashedPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole().toString().toLowerCase());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error in execution");
            return false;
        }
    }
}
