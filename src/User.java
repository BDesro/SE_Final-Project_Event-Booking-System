import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    public enum Role{
        USER,
        ADMIN
    }

    private String username;
    private String hashedPassword;
    private String email;
    private Role role; //Role is important to know which scene the user will go to once they log in

    public User(String username, String plainPassword, String email, Role role) {
        this.username = username;
        this.hashedPassword = PasswordUtils.hashPassword(plainPassword);
        this.email = email;
        this.role = role;
    }

    //Getter methods for user account creation
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public boolean userPassCheck(String inputPassword) { //Calling checkPassword from PasswordUtils in User class
        return PasswordUtils.checkPassword(inputPassword, this.hashedPassword);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String newPlainPassword) {
        this.hashedPassword = PasswordUtils.hashPassword(newPlainPassword);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

}
