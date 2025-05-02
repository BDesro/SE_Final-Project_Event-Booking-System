package edu.westfieldstate.eticketmanager.model;

import edu.westfieldstate.eticketmanager.resources.Avatar;
import edu.westfieldstate.eticketmanager.util.PasswordUtils;

public class User {


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public enum Role{
        USER,
        ADMIN
    }

    private String username;
    private String hashedPassword;
    private String email;
    private Role role; //Role is important to know which scene the user will go to once they log in
    private String nickName;
    private Avatar avatar;

    public User(String username, String plainPassword, String email, Role role, Avatar avatar) {
        this.username = username;
        this.hashedPassword = PasswordUtils.hashPassword(plainPassword);
        this.email = email;
        this.role = role;
        nickName = username;
        this.avatar = avatar;
    }

    public User(String username, String email, String nickName,Avatar avatar) {
        this.username = username;
        this.email = email;
        this.role = Role.USER;
        this.nickName = nickName;
        this.avatar = avatar;
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
        return "edu.westfieldstate.eticketmanager.model.User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

}
