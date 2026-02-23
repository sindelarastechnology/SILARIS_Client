package silaris_client.ReaderConfig;

import java.util.HashSet;
import java.util.Set;

public class ReaderManager {

    private RFIDReaderService readerMasuk;
    private RFIDReaderService readerKeluar;

    private Set<String> epcMasuk = new HashSet<>();
    private Set<String> epcKeluar = new HashSet<>();

    // ===============================
    // READER MASUK
    // ===============================
    public void startMasuk(String portName,
            RFIDReaderService.EPCListener listener) {

        stopMasuk();

        epcMasuk.clear();
        readerMasuk = new RFIDReaderService();

        readerMasuk.start(portName, epc -> {
            if (!epcMasuk.contains(epc)) {
                epcMasuk.add(epc);
                listener.onEPCRead(epc);
            }
        });
    }

    public void stopMasuk() {
        if (readerMasuk != null) {
            readerMasuk.stop();
        }
    }

    // ===============================
    // READER KELUAR
    // ===============================
    public void startKeluar(String portName,
            RFIDReaderService.EPCListener listener) {

        stopKeluar();

        epcKeluar.clear();
        readerKeluar = new RFIDReaderService();

        readerKeluar.start(portName, epc -> {
            if (!epcKeluar.contains(epc)) {
                epcKeluar.add(epc);
                listener.onEPCRead(epc);
            }
        });
    }

    public void stopKeluar() {
        if (readerKeluar != null) {
            readerKeluar.stop();
        }
    }

    public void resetMasuk() {
        epcMasuk.clear();
    }

    public void resetKeluar() {
        epcKeluar.clear();
    }
    
    public void startMasukWithRSSI(String portName,
        RFIDReaderService.TagListener listener) {

        stopMasuk();

        epcMasuk.clear();
        readerMasuk = new RFIDReaderService();

        readerMasuk.startWithRSSI(portName, (epc, rssi) -> {

            if (!epcMasuk.contains(epc)) {
                epcMasuk.add(epc);
            }

            listener.onTag(epc, rssi);
        });
    }
    
    public void startKeluarWithRSSI(String portName,
        RFIDReaderService.TagListener listener) {

        stopKeluar();

        epcKeluar.clear();
        readerKeluar = new RFIDReaderService();

        readerKeluar.startWithRSSI(portName, (epc, rssi) -> {

            if (!epcKeluar.contains(epc)) {
                epcKeluar.add(epc);
            }

            listener.onTag(epc, rssi);
        });
    }
    
    public void startMasukRealtime(String portName,
        RFIDReaderService.TagListener listener) {

        stopMasuk();

        readerMasuk = new RFIDReaderService();

        readerMasuk.startWithRSSI(portName, (epc, rssi) -> {

            // JANGAN pakai epcMasuk.contains lagi
            listener.onTag(epc, rssi);
        });
    }
}