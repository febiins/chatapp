import java.sql.Connection;
import java.sql.PreparedStatement;

public class GroupMessageDAO {

    // Save group chat message into DB
    public static void save(int groupId,
                            int senderId,
                            String encryptedMessage) {

        Connection con = DBConnection.getConnection();
        if (con == null) return;

        String sql =
            "INSERT INTO group_messages " +
            "(group_id, sender_id, message) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, groupId);
            ps.setInt(2, senderId);
            ps.setString(3, encryptedMessage);

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            System.out.println("Group message not saved");
        }
    }
}
