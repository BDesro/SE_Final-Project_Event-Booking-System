package edu.westfieldstate.eticketmanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class JDBC {
    private static final String url = "jdbc:mysql://localhost:3306/etickets";
    private static final String user = "root";
    private static final String password = "Lovegood!17"; //Change to your password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user,password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
