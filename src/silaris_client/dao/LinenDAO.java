/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import silaris_client.config.SQLiteConfig;
import silaris_client.model.Linen;
import silaris_client.model.Penerimaan;

/**
 *
 * @author ASUS
 */
public class LinenDAO {
    
    public Linen findByEpc(String epc) throws Exception {

        String sql = "SELECT * FROM linen  WHERE epc=?";

        try (Connection c = SQLiteConfig.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, epc);

            ResultSet r = ps.executeQuery();
            if (r.next()) {
                Linen l = new Linen();
                l.idLinen = r.getString("id_linen");
                l.epc = r.getString("epc");
                l.kategoriLin = r.getString("kategori");
                l.namaLin = r.getString("nama_linen");
                l.lokasiLin = r.getString("lokasi");
                l.jumlahCuci = r.getInt("jumlah_cuci");
                l.statusLin = r.getString("status");
                l.ketLin = r.getString("keterangan");
                return l;
            }
        }
        return null;
    }
    
    public List<Linen> getByIdPenerimaan(String idPen) throws Exception {
        List<Linen> list = new ArrayList<>();
        String sql = "SELECT * FROM linen  WHERE id_penerimaan=? ORDER BY kategori";

        try (Connection c = SQLiteConfig.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, idPen);
            ResultSet r = ps.executeQuery();

            while (r.next()) {
                Linen n = new Linen();
                n.idLinen = r.getString("id_linen");
                n.epc = r.getString("epc");
                n.kategoriLin = r.getString("kategori");
                n.namaLin = r.getString("nama_linen");
                list.add(n);
            }
        }
        return list;
    }
    
     public List<Linen> getAll() throws Exception {
        List<Linen> list = new ArrayList<>();
        String sql = "SELECT * FROM linen  ORDER BY kategori ASC";

        try (Connection c = SQLiteConfig.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

             while (r.next()) {
                Linen n = new Linen();
//                n.idLinen = r.getString("id_linen");
                n.epc = r.getString("epc");
                n.kategoriLin = r.getString("kategori");
                n.namaLin = r.getString("nama_linen");
                n.lokasiLin = r.getString("lokasi");
                n.jumlahCuci = r.getInt("jumlah_dicuci");
                list.add(n);
            }
        }
        return list;
    }
    
    public List<Linen> getAllDicuci() throws Exception {
        List<Linen> list = new ArrayList<>();
        String sql = "SELECT * FROM linen WHERE keterangan = 'Dicuci' ORDER BY kategori ASC";

        try (Connection c = SQLiteConfig.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                Linen n = new Linen();
//                n.idLinen = r.getString("id_linen");
                n.epc = r.getString("epc");
                n.kategoriLin = r.getString("kategori");
                n.namaLin = r.getString("nama_linen");
                n.lokasiLin = r.getString("lokasi");
                list.add(n);
            }
        }
        return list;
    }
    
    public List<Linen> getAllDipakai() throws Exception {
        List<Linen> list = new ArrayList<>();
        String sql = "SELECT * FROM linen WHERE keterangan = 'Dipakai' ORDER BY kategori ASC";

        try (Connection c = SQLiteConfig.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                Linen n = new Linen();
//                n.idLinen = r.getString("id_linen");
                n.epc = r.getString("epc");
                n.kategoriLin = r.getString("kategori");
                n.namaLin = r.getString("nama_linen");
                n.lokasiLin = r.getString("lokasi");
                list.add(n);
            }
        }
        return list;
    }
    
    public void updateKeluar(Connection c,
                            String idLinen,
                            int jumlahCuci,
                            String lokasiBaru,
                            String keterangan,
                            String status) throws Exception {

       String sql = "UPDATE linen SET " +
               "jumlah_dicuci = ?, " +
               "lokasi = ?, " +
               "status = ?, " +
               "keterangan = ? " +
               "WHERE id_linen = ?";

       try (PreparedStatement p = c.prepareStatement(sql)) {
           p.setInt(1, jumlahCuci);
           p.setString(2, lokasiBaru);
           p.setString(3, status);
           p.setString(4, keterangan);
           p.setString(5, idLinen);
           p.executeUpdate();
       }
   }

}
