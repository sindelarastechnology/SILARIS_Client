package silaris_client.config;

import java.sql.*;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteConfig {
 private static final String APP_NAME = "SILARIS_CLient";
    private static final String DB_NAME = "silaris_client.db";

    public static Connection connect() throws Exception {

        String userHome = System.getProperty("user.home");
        String appDataPath = userHome + "\\AppData\\Local\\" + APP_NAME;
        String dbPath = appDataPath + "\\" + DB_NAME;

        File folder = new File(appDataPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File dbFile = new File(dbPath);

        // Jika database belum ada → copy dari resource
        if (!dbFile.exists()) {
            InputStream is = SQLiteConfig.class.getResourceAsStream("/data/" + DB_NAME);
            Files.copy(is, dbFile.toPath());
        }

        Connection c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        c.createStatement().execute("PRAGMA foreign_keys = ON");

        return c;
    }
}

