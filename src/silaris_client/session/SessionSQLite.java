package silaris_client.session;

import java.sql.*;
import org.json.JSONObject;
import silaris_client.config.SQLiteConfig;
import static silaris_client.config.SQLiteConfig.connect;

public class SessionSQLite {
    public static String getIdRs(){
        String id = null;
        try{
            Connection conn = connect();
            String sql = "SELECT id_rs FROM session LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                id = rs.getString("id_rs");
            }

            rs.close();
            ps.close();
            conn.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return id;
    }

    public static String getKodeRs(){
        String kode = null;
        try{
            Connection conn = connect();
            String sql = "SELECT kode_rs FROM session LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                kode = rs.getString("kode_rs");
            }

            rs.close();
            ps.close();
            conn.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return kode;
    }
    // ================= SIMPAN SESSION =================
    public static void save(JSONObject rs){
        Connection conn = null;
        PreparedStatement ps = null;
        Statement st = null;

        try{
            conn = SQLiteConfig.connect();
            conn.createStatement().execute("PRAGMA busy_timeout = 3000");

            // hapus session lama
            st = conn.createStatement();
            st.executeUpdate("DELETE FROM session");

            String sql = "INSERT INTO session VALUES(?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);

            ps.setString(1, rs.getString("rs_id"));
            ps.setString(2, rs.getString("kode_rs"));
            ps.setString(3, rs.getString("nama_rs"));
            ps.setString(4, rs.getString("email"));
            ps.setString(5, rs.getString("password"));
            ps.setString(6, rs.getString("alamat"));
            ps.setString(7, rs.getString("kontak"));
            ps.setString(8, rs.getString("created_at"));

            ps.executeUpdate();

        }catch(Exception e){
            System.out.println("Save session error: "+e.getMessage());
        }finally{
            try{
                if(ps!=null) ps.close();
                if(st!=null) st.close();
                if(conn!=null) conn.close();
            }catch(Exception ex){}
        }
    }

    // ================= AMBIL SESSION =================
    public static JSONObject get(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try{
            conn = SQLiteConfig.connect();
            conn.createStatement().execute("PRAGMA busy_timeout = 3000");

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM session LIMIT 1");

            if(rs.next()){
                JSONObject obj = new JSONObject();
                obj.put("id_rs", rs.getString("id_rs"));
                obj.put("kode_rs", rs.getString("kode_rs"));
                obj.put("nama_rs", rs.getString("nama_rs"));
                obj.put("email", rs.getString("email"));
                obj.put("password", rs.getString("password"));
                obj.put("alamat", rs.getString("alamat"));
                obj.put("kontak", rs.getString("kontak"));
                obj.put("created_at", rs.getString("created_at"));
                return obj;
            }

        }catch(Exception e){
            System.out.println("Get session error: "+e.getMessage());
        }finally{
            try{
                if(rs!=null) rs.close();
                if(st!=null) st.close();
                if(conn!=null) conn.close();
            }catch(Exception ex){}
        }
        return null;
    }

    // ================= HAPUS SESSION =================
    public static void clear(){
        Connection conn = null;
        Statement st = null;

        try{
            conn = SQLiteConfig.connect();
            conn.createStatement().execute("PRAGMA busy_timeout = 3000");

            st = conn.createStatement();
            st.executeUpdate("DELETE FROM session");

            System.out.println("Session berhasil dihapus");

        }catch(Exception e){
            System.out.println("Clear session error: "+e.getMessage());
        }finally{
            try{
                if(st!=null) st.close();
                if(conn!=null) conn.close();
            }catch(Exception ex){}
        }
    }
}
