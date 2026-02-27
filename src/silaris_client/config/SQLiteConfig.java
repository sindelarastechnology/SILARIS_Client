package silaris_client.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class SQLiteConfig {
//    public static Connection connect() {
//        try {
//            String url = "jdbc:sqlite:data/silaris_client.db";
//            return DriverManager.getConnection(url);
//        } catch (Exception e) {
//            System.out.println("Koneksi gagal: " + e.getMessage());
//            return null;
//        }
//    }
    
    public static Connection connect() throws SQLException {
        String url = "jdbc:sqlite:data/silaris_client.db";
        Connection conn = DriverManager.getConnection(url);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA journal_mode=WAL;");
            stmt.execute("PRAGMA busy_timeout = 5000;");
        }

        return conn;
    }
}

