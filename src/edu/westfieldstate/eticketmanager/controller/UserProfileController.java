package edu.westfieldstate.eticketmanager.controller;

import edu.westfieldstate.eticketmanager.core.SceneID;
import edu.westfieldstate.eticketmanager.core.SceneManager;
import edu.westfieldstate.eticketmanager.model.User;
import edu.westfieldstate.eticketmanager.resources.Avatar;
import edu.westfieldstate.eticketmanager.resources.AvatarManager;
import edu.westfieldstate.eticketmanager.util.JDBC;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {
    @FXML
    private Label userName;
    @FXML
    private Label userNickName;
    @FXML
    private Label userEmail;
    @FXML
    private Label userCC;
    @FXML
    private TextArea nickNameBox;
    @FXML
    private TextArea ccInfoBox;
    @FXML
    private ComboBox<Avatar> avatarBox = new ComboBox<>();
    @FXML
    private ImageView avatarIMG;
    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;

    private User activeUser;


    //==================================================================================================================
    //                                             NON DATABASE FUNCTIONS
    //==================================================================================================================

    public void goBackToGeneral()
    {
        SceneManager.switchTo(SceneID.GENERAL_SCREEN);
    }

    public void switchAvatar()
    {
        activeUser.setAvatar(avatarBox.getValue());
        avatarIMG.setImage(new Image(AvatarManager.getAvatarLocation(activeUser.getAvatar())));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getActiveUser();
        userName.setText(activeUser.getUsername());
        userNickName.setText(activeUser.getNickName());
        userEmail.setText(activeUser.getEmail());
        avatarIMG.setImage(new Image(AvatarManager.getAvatarLocation(activeUser.getAvatar())));
        userCC.setText("Please Enter Your Credit Card Info; We Will Be Selling It On The Black Market");
        avatarBox.getItems().addAll(Avatar.getAll());
        nickNameBox.setText(activeUser.getNickName());
    }
    //==================================================================================================================
    //                                             DATABASE FUNCTIONS
    //==================================================================================================================
    public void getActiveUser()
    {
        String query = "SELECT * FROM users WHERE is_active = 1";

        try(Connection connection = JDBC.getConnection();
            PreparedStatement ps = connection.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                String username = rs.getString("username");
                String email = rs.getString("email_address");
                String nickname = rs.getString("nickname");
                Avatar avatar = Avatar.valueOf(rs.getString("avatar"));
                activeUser = new User(username, email,nickname,avatar);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateActiveUser()
    {
        if (nickNameBox.getText().isEmpty())
        {
            errorLabel.setText("You Cannot Have An Empty Nick Name");
            successLabel.setText("");
        }
        else {
            String query = "UPDATE users SET nickname = ?, avatar = ? WHERE is_active = 1;";
            try (Connection connection = JDBC.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, nickNameBox.getText());
                statement.setString(2, String.valueOf(activeUser.getAvatar()));
                statement.executeUpdate();
                successLabel.setText("Profile Saved");
                errorLabel.setText("");
                statement.close();
                userNickName.setText(nickNameBox.getText());
            } catch (Exception e) {
                successLabel.setText("");
                errorLabel.setText("Cannot Save Profile: " + e.getMessage());
            }
        }

    }

}
