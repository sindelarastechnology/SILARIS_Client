/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import silaris_client.config.SQLiteConfig;
import silaris_client.model.Linen;

/**
 *
 * @author ASUS
 */
public class LinenDAO {
    
    
//    Get ALL
    
    
    
    
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
}
