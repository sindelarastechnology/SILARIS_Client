package silaris_client.service;

import okhttp3.*;
import org.json.JSONArray;
import silaris_client.config.configSupabase;
import silaris_client.session.SessionSQLite;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
            "&select=pengiriman_id,rs_id,kode_rs,total_linen,linen_dikirim(*)";

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
    
    public static boolean updateStatusPengiriman(String pengirimanId){

        try{

            OkHttpClient client = configSupabase.client;

            String url = configSupabase.SUPABASE_URL +
                    "pengiriman?pengiriman_id=eq." + pengirimanId;
            
            ZoneId zone = ZoneId.of("Asia/Jakarta");

            ZonedDateTime now = ZonedDateTime.now(zone);

            String tanggal = now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            String json = "{"
                    + "\"status\":\"DITERIMA\","
                    + "\"tanggal_diterima\":\"" + tanggal + "\""
                    + "}";

            MediaType JSON = MediaType.parse("application/json");

            RequestBody body = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .url(url)
                    .patch(body)
                    .addHeader("apikey", configSupabase.SUPABASE_API_KEY)
                    .addHeader("Authorization",
                            "Bearer " + configSupabase.SUPABASE_API_KEY)
                    .addHeader("Prefer", "return=minimal")
                    .build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                return true;
            }else{
                System.out.println("Update gagal: "
                        + response.code()
                        + " - "
                        + response.body().string());
            }

        }catch(Exception e){
            System.out.println("Error update status: "+e.getMessage());
        }

        return false;
    }
    
}
