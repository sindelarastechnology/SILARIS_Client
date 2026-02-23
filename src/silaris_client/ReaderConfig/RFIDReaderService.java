
package silaris_client.ReaderConfig;

import com.fazecast.jSerialComm.SerialPort;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.io.InputStream;

public class RFIDReaderService {
    private SerialPort port;
    private boolean running;
    private Thread thread;

    public void start(String portName, EPCListener listener) {

        port = SerialPort.getCommPort(portName);
        port.setBaudRate(115200);
        port.setNumDataBits(8);
        port.setNumStopBits(SerialPort.ONE_STOP_BIT);
        port.setParity(SerialPort.NO_PARITY);
        port.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_BLOCKING, 500, 0);

        if (!port.openPort()) {
            throw new RuntimeException("Gagal buka port " + portName);
        }

        // START MULTI READ
        port.writeBytes(ReaderCommand.CMD_MULTI,
                ReaderCommand.CMD_MULTI.length);

        running = true;

        thread = new Thread(() -> {
            try (InputStream in = port.getInputStream()) {

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                while (running) {
                    int b = in.read();
                    if (b == -1) continue;

                    buffer.write(b);

                    if (b == 0x7E) { // frame end
                        byte[] frame = buffer.toByteArray();
                        buffer.reset();

                        String epc = EPCFrameParser.parse(frame);
                        if (epc != null) {
                            listener.onEPCRead(epc);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }
    
    public void startWithRSSI(String portName, TagListener listener) {

        port = SerialPort.getCommPort(portName);
        port.setBaudRate(115200);
        port.setNumDataBits(8);
        port.setNumStopBits(SerialPort.ONE_STOP_BIT);
        port.setParity(SerialPort.NO_PARITY);
        port.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_BLOCKING, 500, 0);

        if (!port.openPort()) {
            throw new RuntimeException("Gagal buka port " + portName);
        }

        port.writeBytes(ReaderCommand.CMD_MULTI,
                ReaderCommand.CMD_MULTI.length);

        running = true;

        thread = new Thread(() -> {
            try (InputStream in = port.getInputStream()) {

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                while (running) {

                    int b = in.read();
                    if (b == -1) continue;

                    buffer.write(b);

                    if (b == 0x7E) {

                        byte[] frame = buffer.toByteArray();
                        buffer.reset();

                        String epc = EPCFrameParser.parse(frame);

                        // sementara dummy RSSI
                        int rssi = -60;

                        if (epc != null) {
                            listener.onTag(epc, rssi);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    public void stop() {
        running = false;

        try {
            port.writeBytes(ReaderCommand.CMD_STOP,
                    ReaderCommand.CMD_STOP.length);
            Thread.sleep(100);

            port.writeBytes(ReaderCommand.CMD_IDLE,
                    ReaderCommand.CMD_IDLE.length);
            Thread.sleep(100);

        } catch (Exception ignored) {}

        if (port != null && port.isOpen()) {
            port.closePort();
        }
    }
    public interface EPCListener {
        void onEPCRead(String epc);
    }
    public interface TagListener {
        void onTag(String epc, int rssi);
    }
}
