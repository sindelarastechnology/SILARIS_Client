package silaris_client.config;


import com.fazecast.jSerialComm.SerialPort;
import java.util.function.Consumer;

public class Rmt01ReaderService {

    private SerialPort port;
    private boolean running = false;
    private Consumer<String> onEpcRead;

    public Rmt01ReaderService(Consumer<String> onEpcRead) {
        this.onEpcRead = onEpcRead;
    }

    public boolean connect() {
        port = SerialPort.getCommPort("COM6");
        port.setBaudRate(115200);
        port.setNumDataBits(8);
        port.setParity(SerialPort.NO_PARITY);
        port.setNumStopBits(SerialPort.ONE_STOP_BIT);
        return port.openPort();
    }

    public void startInventory() {
        running = true;
        sendMultipleInventoryCmd();

        new Thread(() -> {
            byte[] buffer = new byte[1024];
            while (running) {
                int n = port.readBytes(buffer, buffer.length);
                if (n > 0) {
                    parse(buffer, n);
                }
            }
        }).start();
    }

    public void stopInventory() {
        running = false;
        sendStopCmd();
    }

    private void parse(byte[] data, int len) {
        for (int i = 0; i < len - 1; i++) {
            if ((data[i] & 0xFF) == 0xBB && (data[i+1] & 0xFF) == 0x02) {
                int epcLen = data[i + 6] & 0xFF;
                StringBuilder epc = new StringBuilder();
                for (int j = 0; j < epcLen; j++) {
                    epc.append(String.format("%02X", data[i + 7 + j]));
                }
                onEpcRead.accept(epc.toString());
            }
        }
    }

    private void sendMultipleInventoryCmd() {
        byte[] cmd = new byte[]{
            (byte)0xBB, 0x00, 0x27, 0x00, 0x03,
            0x22, 0x27, 0x10,
            (byte)0x83, 0x7E
        };
        port.writeBytes(cmd, cmd.length);
    }

    private void sendStopCmd() {
        byte[] cmd = new byte[]{
            (byte)0xBB, 0x00, 0x28, 0x00, 0x00, 0x28, 0x7E
        };
        port.writeBytes(cmd, cmd.length);
    }
}
