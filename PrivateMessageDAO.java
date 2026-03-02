import java.sql.Connection;
import java.sql.PreparedStatement;

public class PrivateMessageDAO {

    // Save private chat message into DB
    public static void save(int senderId,
                            int receiverId,
                            String encryptedMessage) {

        Connection con = DBConnection.getConnection();
        if (con == null) return;

        String sql =
            "INSERT INTO private_chats " +
            "(sender_id, receiver_id, message) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, encryptedMessage);

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            System.out.println("Private message not saved");
        }
    }
}