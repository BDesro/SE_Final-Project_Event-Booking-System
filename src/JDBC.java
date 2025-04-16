import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class JDBC {
    private static final String url = "jdbc:mysql://localhost:3306/etickets";
    private static final String user = "root";
    private static final String password = "Brokenstar48!"; //Change to your password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user,password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Test in main class to make sure it connects properly
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Connected to MySQL!");
        } else {
            System.out.println("Failed to connect.");
        }
    }
}
