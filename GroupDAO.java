import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GroupDAO {

    // Get group_id using group name
    public static int getGroupId(String groupName) {

        Connection con = DBConnection.getConnection();
        if (con == null) return -1;

        String sql = "SELECT group_id FROM groups WHERE group_name = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, groupName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("group_id");
                con.close();
                return id;
            }

            con.close();

        } catch (Exception e) {
            System.out.println("Group ID fetch failed");
        }

        return -1;
    }

    // Add user to group (group_members table)
    public static void addMember(int groupId, int userId) {

        Connection con = DBConnection.getConnection();
        if (con == null) return;

        String sql =
            "INSERT INTO group_members (group_id, user_id) VALUES (?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, groupId);
            ps.setInt(2, userId);

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            // ignore if already a member
        }
    }
}
