import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // 🔹 Register user
   public static boolean register(String username, String password) {

    Connection con = DBConnection.getConnection();
    if (con == null) {
        System.out.println("DB connection is NULL");
        return false;
    }

    String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, PasswordUtil.hash(password));

        ps.executeUpdate();
        con.commit();   // IMPORTANT for Oracle

        con.close();
        return true;

    } catch (Exception e) {
        System.out.println("Registration error:");
        e.printStackTrace();   // 🔥 THIS LINE IS CRITICAL
        return false;
    }
}

    // 🔹 Login user
    public static boolean login(String username, String password) {

        Connection con = DBConnection.getConnection();
        if (con == null) return false;

        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?");

            ps.setString(1, username);
            ps.setString(2, PasswordUtil.hash(password));

            ResultSet rs = ps.executeQuery();
            boolean result = rs.next();

            con.close();
            return result;

        } catch (Exception e) {
            System.out.println("Login failed");
            return false;
        }
    }

    // 🔹 Get user_id (USED BY CHAT SYSTEM)
    public static int getUserId(String username) {

        Connection con = DBConnection.getConnection();
        if (con == null) return -1;

        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT user_id FROM users WHERE username=?");

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("user_id");
                con.close();
                return id;
            }

            con.close();

        } catch (Exception e) {
            System.out.println("User ID fetch failed");
        }

        return -1;
    }
}