package silaris_client.session;

import java.util.prefs.Preferences;

public class UserSession {

    private static Preferences pref = Preferences.userRoot().node("silaris_login");

    // simpan session
    public static void setLogin(String email){
        pref.put("email", email);
        pref.putBoolean("isLogin", true);
    }

    // cek sudah login belum
    public static boolean isLogin(){
        return pref.getBoolean("isLogin", false);
    }

    // ambil email login
    public static String getEmail(){
        return pref.get("email", "");
    }

    // logout
    public static void clear(){
        pref.putBoolean("isLogin", false);
        pref.remove("email");
    }
}
