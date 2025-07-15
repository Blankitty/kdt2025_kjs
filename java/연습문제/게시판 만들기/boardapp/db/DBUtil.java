package boardapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String db_hostname = "localhost";
    private static final int db_portnumber = 3306;
    private static final String db_database = "board_db";
    private static final String db_charset = "utf8";
    private static final String db_username = "root";
    private static final String db_password = "1234";

    private Connection conn = null;

    private static DBUtil current;

    private DBUtil() {
    }

    public static DBUtil getInstance() {
        if (current == null) {
            current = new DBUtil();
        }
        return current;
    }

    public static void freeInstance() {
        current = null;
    }

    public Connection open() {
        try {
            if (conn == null || conn.isClosed()) {
                String urlFormat = "jdbc:mysql://%s:%d/%s?characterEncoding=%s";
                String url = String.format(urlFormat, db_hostname, db_portnumber, db_database, db_charset);

                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, db_username, db_password);
                System.out.println("=== DATABASE Connect Success ===");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("=== DATABASE Connect Fail ===");
            e.printStackTrace();
        }

        return conn;
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("=== DATABASE Disconnect Success ===");
            } catch (Exception e) {
                System.out.println("=== DATABASE Disconnect Fail ===");
                e.printStackTrace();
            }
            conn = null;
        }
    }

    
    public static Connection getConnection() {
        return DBUtil.getInstance().open();
    }
}
