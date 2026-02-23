package silaris_client.dao;

import silaris_client.model.Ruangan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import silaris_client.config.SQLiteConfig;


public class RuanganDAO {

    public void insert(Ruangan r) throws Exception {
        String sql = "INSERT INTO ruangan(id, kode_ruangan, nama_ruangan, keterangan) VALUES(?,?,?,?)";

        try (Connection c = SQLiteConfig.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, r.id);
            ps.setString(2, r.kodeRuangan);
            ps.setString(3, r.namaRuangan);
            ps.setString(4, r.keterangan);
            ps.executeUpdate();
        }
    }

    public void update(Ruangan r) throws Exception {
        String sql = "UPDATE ruangan SET kode_ruangan=?, nama_ruangan=?, keterangan=? WHERE id=?";

        try (Connection c = SQLiteConfig.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, r.kodeRuangan);
            ps.setString(2, r.namaRuangan);
            ps.setString(3, r.keterangan);
            ps.setString(4, r.id);
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws Exception {
        String sql = "DELETE FROM ruangan WHERE id=?";

        try (Connection c = SQLiteConfig.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    public List<Ruangan> getAll() throws Exception {
        List<Ruangan> list = new ArrayList<>();
        String sql = "SELECT * FROM ruangan ORDER BY id ASC";

        try (Connection c = SQLiteConfig.connect();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                Ruangan r = new Ruangan();
                r.id = rs.getString("id");
                r.kodeRuangan = rs.getString("kode_ruangan");
                r.namaRuangan = rs.getString("nama_ruangan");
                r.keterangan = rs.getString("keterangan");
                list.add(r);
            }
        }

        return list;
    }
    
    public Ruangan getById(int id) throws Exception {
       String sql = "SELECT * FROM ruangan WHERE id=?";

       try (Connection c = SQLiteConfig.connect();
            PreparedStatement ps = c.prepareStatement(sql)) {

           ps.setInt(1, id);
           ResultSet rs = ps.executeQuery();

           if (rs.next()) {
               Ruangan r = new Ruangan();
               r.id = rs.getString("id");
               r.kodeRuangan = rs.getString("kode_ruangan");
               r.namaRuangan = rs.getString("nama_ruangan");
               r.keterangan = rs.getString("keterangan");

               return r;
           }
       }

       return null;
   }
    
   public Integer getIdByKode(String kode) throws Exception {
        String sql = "SELECT id FROM ruangan WHERE kode_ruangan=?";

        try (Connection c = SQLiteConfig.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, kode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        return null;
    }


}
