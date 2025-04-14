# SE_Final-Project_Event-Booking-System

JAVAFX VM
--module-path "C:\Program Files\Java\javafx-sdk-24\lib" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics --enable-native-access=ALL-UNNAMED

/*
public static List<String> getItemTypes() {
List<String> itemTypes = FXCollections.observableArrayList();
String query = "SELECT type_name FROM item_types";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                itemTypes.add(rs.getString("type_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemTypes;
    }
*/