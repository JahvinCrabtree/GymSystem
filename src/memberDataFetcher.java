import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class memberDataFetcher {

    private Connection connect;

    public int fetchMemberNumByUsername(String username) {
        connect = dbConnection.getConnection();
        
        int memberNum = -1; // default value indicating failure to find
        String fetchMemberNumSQL = "SELECT id FROM member_table WHERE username = ?";
        
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(fetchMemberNumSQL);
            preparedStatement.setString(1, username);
            
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                memberNum = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Clean up resources, close ResultSet, PreparedStatement, etc.
        }

        return memberNum;
    }

    public static String username;
    public static String path;
}
