import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "febin",
                "febin"
            );

            con.setAutoCommit(false); // IMPORTANT
            return con;

        } catch (Exception e) {
            System.out.println("DB connection failed:");
            e.printStackTrace();
            return null;
        }
    }
}
