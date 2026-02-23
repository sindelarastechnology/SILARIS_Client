package silaris_client.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteConfig {
    public static Connection connect() {
        try {
            String url = "jdbc:sqlite:data/silaris_client.db";
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}

