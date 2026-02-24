package silaris_client.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import silaris_client.config.SQLiteConfig;
import silaris_client.model.LogLinen;

public class LogLinenDAO {

    // ================= INSERT =================
    public void insert(LogLinen log) {

        String sql = "INSERT INTO log_linen (id_log,tanggal, petugas, ruangan) VALUES (?,?, ?, ?)";

        try (Connection c = SQLiteConfig.connect();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, log.getIdLog());
            p.setString(2, log.getTanggal());
            p.setString(3, log.getPetugas());
            p.setString(4, log.getRuangan());

            p.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= GET ALL =================
    public List<LogLinen> getAll() {

        List<LogLinen> list = new ArrayList<>();
        String sql = "SELECT * FROM log_linen ORDER BY tanggal ASC";

        try (Connection c = SQLiteConfig.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {

                LogLinen log = new LogLinen();
                log.setIdLog(r.getString("id_log"));
                log.setTanggal(r.getString("tanggal"));
                log.setPetugas(r.getString("petugas"));
                log.setRuangan(r.getString("ruangan"));

                list.add(log);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= DELETE =================
    public void delete(int id) {

        String sql = "DELETE FROM log_linen WHERE id_log = ?";

        try (Connection c = SQLiteConfig.connect();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);
            p.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}