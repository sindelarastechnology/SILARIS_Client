package silaris_client.service;

import okhttp3.*;
import org.json.JSONArray;
import silaris_client.config.configSupabase;
import silaris_client.session.SessionSQLite;

public class PengirimanService {

    public static JSONArray getLinenByKode(String kodeVerifikasi){
        try{
            OkHttpClient client = configSupabase.client;

            String idRs   = SessionSQLite.getIdRs();
            String kodeRs = SessionSQLite.getKodeRs();
            String status = "DIKIRIM";

            String url = configSupabase.SUPABASE_URL +
            "pengiriman?kode_verifikasi=eq."+kodeVerifikasi+
            "&rs_id=eq."+idRs+
            "&kode_rs=eq."+kodeRs+
            "&status=eq."+status+
            "&select=pengiriman_id,rs_id,kode_rs,linen_dikirim(*)";

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("apikey", configSupabase.SUPABASE_API_KEY)
                    .addHeader("Authorization","Bearer "+configSupabase.SUPABASE_API_KEY)
                    .build();

            Response response = client.newCall(request).execute();
            String res = response.body().string();

            return new JSONArray(res);

        }catch(Exception e){
            System.out.println("Error get linen: "+e.getMessage());
        }
        return null;
    }
}
