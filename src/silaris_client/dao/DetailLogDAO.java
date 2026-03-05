/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import silaris_client.config.SQLiteConfig;
import silaris_client.model.DetailLog;

public class DetailLogDAO {

    public void insert(Connection c,
                       String idDetail,
                       String idLog,
                       String idLinen,
                       String epc,
                       String kategori,
                       String namaLinen,
                       int jumlahCuci,
                       String lokasiAsal,
                       String lokasi,
//                       String status,
                       String keterangan) throws Exception {

        String sql = "INSERT INTO detail_log " +
                "(id_detail_log,id_log,id_linen,epc,kategori,nama_linen," +
                "jumlah_cuci,lokasi_asal,lokasi,keterangan) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, idDetail);
            p.setString(2, idLog);
            p.setString(3, idLinen);
            p.setString(4, epc);
            p.setString(5, kategori);
            p.setString(6, namaLinen);
            p.setInt(7, jumlahCuci);
            p.setString(8, lokasiAsal);
            p.setString(9, lokasi);
//            p.setString(10, status);
            p.setString(10, keterangan);

            p.executeUpdate();
        }
    }
    
    public List<DetailLog> getByIdLog(String idLog) throws Exception {
        List<DetailLog> list = new ArrayList<>();
        String sql = "SELECT * FROM detail_log  WHERE id_log =? ORDER BY kategori";

        try (Connection c = SQLiteConfig.connect();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, idLog);
            ResultSet r = ps.executeQuery();

            while (r.next()) {
                DetailLog n = new DetailLog();
                n.idLinenLog = r.getString("id_linen");
                n.epcLog = r.getString("epc");
                n.kategoriLog = r.getString("kategori");
                n.namaLinenLog = r.getString("nama_linen");
                n.jumlahCuciLog = r.getInt("jumlah_cuci");
                n.keteranganLog = r.getString("keterangan");
                list.add(n);
            }
        }
        return list;
    }
}
