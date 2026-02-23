
package silaris_client.panel;

import com.fazecast.jSerialComm.SerialPort;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import silaris_client.ReaderConfig.ReaderManager;

public class PanelReaderTest extends javax.swing.JPanel {

    private JComboBox<String> cbPort = new JComboBox<>();
    private JButton btnStart = new JButton("Start Scan");
    private JButton btnStop = new JButton("Stop Scan");
    private JButton btnReset = new JButton("Reset");
    private JTable table;
    private DefaultTableModel model;

    private ReaderManager manager;
    private boolean isMasuk;
    
    public PanelReaderTest(ReaderManager manager, boolean isMasuk) {
//        initComponents();
        this.manager = manager;
        this.isMasuk = isMasuk;
        init();
    }

    private void init() {

        setLayout(new BorderLayout());

        model = new DefaultTableModel(
                new Object[]{"No", "EPC"}, 0);

        table = new JTable(model);

        JPanel top = new JPanel();

        top.add(new JLabel(isMasuk ?
                "Reader Masuk Port:" :
                "Reader Keluar Port:"));

        top.add(cbPort);
        top.add(btnStart);
        top.add(btnStop);
        top.add(btnReset);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadPorts();

        btnStart.addActionListener(e -> start());
        btnStop.addActionListener(e -> stop());
        btnReset.addActionListener(e -> reset());
    }

    private void loadPorts() {
        cbPort.removeAllItems();

        for (SerialPort port : SerialPort.getCommPorts()) {

            String desc = port.getPortDescription();
            String manufacturer = port.getDescriptivePortName();

            // Filter hanya CP210x / Silicon Labs
            if (desc != null && desc.toLowerCase().contains("cp210")
                    || manufacturer != null && manufacturer.toLowerCase().contains("silicon")) {

                cbPort.addItem(port.getSystemPortName());
            }
        }
    }

    private void start() {

        String portName =
                (String) cbPort.getSelectedItem();

        if (portName == null) return;

        if (isMasuk) {

            manager.startMasuk(portName, epc ->
                    addToTable(epc));

        } else {

            manager.startKeluar(portName, epc ->
                    addToTable(epc));
        }
    }

    private void stop() {
        if (isMasuk)
            manager.stopMasuk();
        else
            manager.stopKeluar();
    }

    private void reset() {

        model.setRowCount(0);

        if (isMasuk)
            manager.resetMasuk();
        else
            manager.resetKeluar();
    }

    private void addToTable(String epc) {

        SwingUtilities.invokeLater(() -> {

            model.addRow(new Object[]{
                    model.getRowCount() + 1,
                    epc
            });
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
