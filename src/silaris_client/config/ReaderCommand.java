/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.config;

/**
 *
 * @author ASUS
 */
public class ReaderCommand {
    public static final byte[] CMD_MULTI = hex("BB00270003222710837E");
    public static final byte[] CMD_STOP  = hex("BB00280000287E");
    public static final byte[] CMD_IDLE  = hex("BB00040003010101207E");

    private static byte[] hex(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)
                    ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
