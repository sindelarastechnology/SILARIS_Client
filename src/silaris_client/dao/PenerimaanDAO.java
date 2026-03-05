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
import silaris_client.model.Penerimaan;
import silaris_client.model.Linen;

/**
 *
 * @author ASUS
 */
public class PenerimaanDAO {
    
    public List<Penerimaan> getAll() throws Exception {
        List<Penerimaan> list = new ArrayList<>();
        String sql = "SELECT * FROM penerimaan ORDER BY tanggal";

        try (Connection c = SQLiteConfig.connect();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                Penerimaan k = new Penerimaan();
                k.idPen = r.getString("id_penerimaan");
                k.tanggalPen = r.getString("tanggal");
                k.totalLin = r.getInt("jumlah_linen");
                k.petugasPen = r.getString("petugas");
                k.ketPen = r.getString("keterangan");
                list.add(k);
            }
        }
        return list;
    }
    
    

    
}