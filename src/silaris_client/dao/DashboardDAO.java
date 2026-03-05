/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import silaris_client.config.SQLiteConfig;

public class DashboardDAO {

    public int getTotalLinen() {
        return getCount("SELECT COUNT(*) FROM linen");
    }

    public int getTotalDicuci() {
        return getCount(
            "SELECT COUNT(*) FROM linen WHERE keterangan = 'Dicuci'");
    }

    public int getTotalDipakai() {
        return getCount(
            "SELECT COUNT(*) FROM linen WHERE keterangan = 'Dipakai'");
    }

    public int getTotalRuangan() {
        return getCount("SELECT COUNT(*) FROM ruangan");
    }

    // ===============================
    // METHOD GENERIC SUPAYA TIDAK DUPLIKAT
    // ===============================
    private int getCount(String sql) {
        int total = 0;

        try (Connection c = SQLiteConfig.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            if (r.next()) {
                total = r.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}

