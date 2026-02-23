package silaris_client.service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import silaris_client.config.configSupabase;

public class LoginService {

    public static JSONObject login(String email, String password){
    try{
        OkHttpClient client = configSupabase.client;

        String url = configSupabase.SUPABASE_URL +
                "rumah_sakit?email=eq."+email+
                "&password=eq."+password+
                "&select=*";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("apikey", configSupabase.SUPABASE_API_KEY)
                .addHeader("Authorization","Bearer "+configSupabase.SUPABASE_API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        String res = response.body().string();

        org.json.JSONArray arr = new org.json.JSONArray(res);

        if(arr.length()>0){
            return arr.getJSONObject(0); // data RS
        }

    }catch(Exception e){
        System.out.println(e.getMessage());
    }
    return null;
    }

    }

