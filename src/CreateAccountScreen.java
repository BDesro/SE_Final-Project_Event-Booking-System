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
        TextInputDialog dialog = new TextInputDialog(); //Might need to remove since title already sets title
        dialog.setTitle("Create Account");
        dialog.setHeaderText("Enter your details (username,email,password)");

        Label message = new Label("");

        Label newUserLabel = new Label("New Username:");
        TextField newUser = new TextField();
        Label newEmailLabel = new Label("New Email:");
        TextField newEmail = new TextField();
        Label newPassLabel = new Label("New Password:");
        PasswordField newPass = new PasswordField();
        Button returnToLogIn = new Button ("Back to Log In");
        Button submitNewAccount = new Button("Submit New Account");

        submitNewAccount.setOnAction(submitEvent -> {
            String newUsername = newUser.getText().trim();
            String newEmails = newEmail.getText().trim();
            String newPassword = newPass.getText();

            if (newUsername.isEmpty() || newEmails.isEmpty() || newPassword.isEmpty()) {
                message.setText("All fields are required.");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            if (emailHasAccount(newEmails)) {
                message.setText("Email already has associated account!!!!");
                message.setStyle("-fx-text-fill: red;");
                newEmail.clear();
            } else {
                User user = new User(newUsername, newPassword, newEmails, User.Role.USER); //Default as user account
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
        HBox passRow = new HBox(10, newPassLabel, newPass);
        HBox buttonRow = new HBox(10, submitNewAccount, returnToLogIn);
        root.getChildren().addAll(userRow, emailRow, passRow, buttonRow, message);
        
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
