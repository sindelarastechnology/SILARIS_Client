/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.ReaderConfig;

/**
 *
 * @author ASUS
 */
public class EPCFrameParser {
    public static String parse(byte[] data) {
        if (data.length < 10) return null;

        // data[1] == 0x02 && data[2] == 0x22
        if ((data[1] & 0xFF) == 0x02 && (data[2] & 0xFF) == 0x22) {

            int length = ((data[3] & 0xFF) << 8) | (data[4] & 0xFF);
            if (data.length < 5 + length) return null;

            byte[] payload = new byte[length];
            System.arraycopy(data, 5, payload, 0, length);

            if (payload.length < 5) return null;

            // payload[3:-2]
            int epcLen = payload.length - 5;
            byte[] epcBytes = new byte[epcLen];
            System.arraycopy(payload, 3, epcBytes, 0, epcLen);

            return bytesToHex(epcBytes);
        }
        return null;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
