/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.dao;

import silaris_client.config.SQLiteConfig;
import java.sql.*;
import java.util.*;

/**
 *
 * @author ASUS
 */
public class ReaderConfigDAO {
    public String getPortMasuk() {
        return getPort("reader_masuk_port");
    }

    public String getPortKeluar() {
        return getPort("reader_keluar_port");
    }

    private String getPort(String column) {

        try (Connection c = SQLiteConfig.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(
                     "SELECT " + column + " FROM reader_config WHERE id=1")) {

            if (r.next()) return r.getString(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updatePort(String masuk, String keluar) {

        try (Connection c = SQLiteConfig.connect();
             PreparedStatement ps = c.prepareStatement(
                     "UPDATE reader_config SET reader_masuk_port=?, reader_keluar_port=? WHERE id=1")) {

            ps.setString(1, masuk);
            ps.setString(2, keluar);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
