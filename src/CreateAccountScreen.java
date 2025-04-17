import javafx.geometry.Insets;
import javafx.scene.Node;
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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Account");
        dialog.setHeaderText("Enter your details (username,email,password)");

        Label newUserLabel = new Label("New Username:");
        TextField newUserField = new TextField();
        Label newEmailLabel = new Label("New Email:");
        TextField newEmailField = new TextField();
        Label newPassLabel = new Label("New Password:");
        PasswordField newPassField = new PasswordField();
        Button returnToLogIn = new Button ("Back to Log In");
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
        returnToLogIn.setOnAction(e ->
            SceneManager.switchTo(SceneID.LOGIN_SCREEN));
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        HBox userRow = new HBox(10, newUserLabel, newUserField);
        HBox emailRow = new HBox(10, newEmailLabel, newEmailField);
        HBox passRow = new HBox(10, newPassLabel, newPassField);
        HBox buttonRow = new HBox(10, submitNewAccount, returnToLogIn);
        root.getChildren().addAll(userRow, emailRow, passRow, buttonRow);
        
        return root;
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

    public static boolean createAccount(User user){ //Creating account method
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
