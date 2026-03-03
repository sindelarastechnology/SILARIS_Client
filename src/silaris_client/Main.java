
package silaris_client;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import org.json.JSONObject;
import silaris_client.ReaderConfig.ReaderManager;
import silaris_client.config.RFIDTestReaderService;
import silaris_client.config.SerialPortUtil;
import silaris_client.menu.MenuDashboard;
import silaris_client.menu.MenuLaundry;
import silaris_client.panel.PanelLogin;
import silaris_client.menu.MenuMasterData;
import silaris_client.menu.MenuSetting;
import silaris_client.panel.PanelLogLinen;
import silaris_client.panel.PanelSinkronasiData;
import silaris_client.panel.Panel_LinenKeluar;
import silaris_client.panel.Panel_LinenMasuk;
import silaris_client.session.SessionSQLite;
import silaris_client.sweetAlert.SweetAlert_Logout;


/**
 *
 * @author HP
 */
public class Main extends javax.swing.JFrame {
    private RFIDTestReaderService readerService = new RFIDTestReaderService();
    ReaderManager manager = new ReaderManager();
    JSONObject ses = SessionSQLite.get();
    
    private MenuDashboard dashboard;
    private MenuMasterData masterdatamenu;
    private MenuLaundry laundrymenu;
    private MenuSetting settingmenu;
    
    private Panel_LinenMasuk panelMasuk;
    private Panel_LinenKeluar panelKeluar;
    private PanelLogLinen panelLog;
    private PanelSinkronasiData panelSinkronasiData;
    
    private boolean adjustingPort = false;
    
    public Main() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        panelMasuk = new Panel_LinenMasuk(this);
        panelKeluar = new Panel_LinenKeluar(this);
        panelLog = new PanelLogLinen(this);
        panelSinkronasiData = new PanelSinkronasiData(this);
        
        loadPorts();
        updateReaderVisibility();
        
        updateScanUI(ReaderType.MASUK, false);
        updateScanUI(ReaderType.KELUAR, false);
        
        dashboard = new MenuDashboard(this);
        masterdatamenu = new MenuMasterData(this);
        laundrymenu = new MenuLaundry(this, panelKeluar, panelMasuk);
        settingmenu = new MenuSetting(panelSinkronasiData);
        
        tabbedPaneUtama.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tabbedPaneUtama.addTab("Dashboard", dashboard);
        tabbedPaneUtama.addTab("Master Data", masterdatamenu);
        tabbedPaneUtama.addTab("Laundry ", laundrymenu);
        tabbedPaneUtama.addTab("Setting ", settingmenu);
        
        tabbedPaneUtama.addChangeListener(e -> {

            int selectedIndex = tabbedPaneUtama.getSelectedIndex();
            String title = tabbedPaneUtama.getTitleAt(selectedIndex);

            if (title.equals("Dashboard")) {
                dashboard.loadCounts();
            }
            if (title.equals("Laundry ")) {
                laundrymenu.loadData();
            }
            if (title.equals("Master Data")) {
                masterdatamenu.loadLinen();
                masterdatamenu.loadRuangan();
            }
            if (title.equals("Setting ")) {
                settingmenu.loadLog();
            }
        });
        
        
        // ambil nama RS dari session
        String namaRS = ses.getString("nama_rs");

        // load icon
        ImageIcon iconUser = new ImageIcon(getClass().getResource("/silaris_client_assets/profil.png"));
        Image img = iconUser.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH);
        iconUser = new ImageIcon(img);

        // set label
        lbUser.setText(" " + namaRS);
        lbUser.setIcon(iconUser);
        lbUser.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lbUser.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // jarak icon & teks
        lbUser.setIconTextGap(8);

        // ================= POPUP =================
        JPopupMenu popup = new JPopupMenu();
        popup.setBorder(BorderFactory.createEmptyBorder());
        popup.setBackground(Color.WHITE);

        // card panel
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(12,18,12,18)
        ));
        // AGAR CARD MELEBAR
        card.setPreferredSize(new Dimension(120, 90)); 

        
        JLabel lbRole = new JLabel("Profil");
        lbRole.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        lbRole.setForeground(Color.GRAY);
        lbRole.setAlignmentX(Component.LEFT_ALIGNMENT);

        // garis
        JSeparator garis = new JSeparator();

        // tombol logout teks
        JLabel btnLogout = new JLabel("Logout");
        btnLogout.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        btnLogout.setForeground(new Color(180,0,0));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setAlignmentX(Component.LEFT_ALIGNMENT);

        // hover merah terang
        btnLogout.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                btnLogout.setForeground(Color.RED);
            }
            public void mouseExited(MouseEvent e){
                btnLogout.setForeground(new Color(180,0,0));
            }
        });

        // aksi logout
        btnLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                SweetAlert_Logout.show(
                        (Frame) SwingUtilities.getWindowAncestor(btnLogout),
                        () -> {
                            SessionSQLite.clear();
                            new PanelLogin().setVisible(true);
                            dispose();
                        }
                );
            }
        });

        // susun card
//        card.add(lbNama);
        card.add(lbRole);
        card.add(Box.createVerticalStrut(10));
        card.add(garis);
        card.add(Box.createVerticalStrut(10));
        card.add(btnLogout);

        popup.add(card);

        // klik label tampil dropdown
        lbUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popup.show(lbUser, 0, lbUser.getHeight() + 5);

            }
        });
        lbUser.setVisible(false);
        popup.setVisible(false);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                
                readerService.stop();
                manager.stopMasuk();
            }
        });
        
        cbPortKeluar.addActionListener(e -> validatePortSelection());
        cbPortMasuk.addActionListener(e -> validatePortSelection());
        
        
    }

    private void updateScanUI(ReaderType type, boolean scanning) {

        JLabel targetLabel;

        if (type == ReaderType.MASUK) {
            targetLabel = lblStatus;
        } else {
            targetLabel = lblStatus12;
        }

        if (scanning) {
            targetLabel.setText("● SCANNING");
            targetLabel.setForeground(new Color(0, 153, 0));
        } else {
            targetLabel.setText("● IDLE");
            targetLabel.setForeground(Color.GRAY);
        }
    }
    
    private void updateReaderVisibility() {

        int totalPort = cbPortKeluar.getItemCount();

        if (totalPort <= 1) {
            panelReader.setVisible(false);
            jLabel2.setText("Reader Status ");
        } else {
            panelReader.setVisible(true);
        }

        revalidate();
        repaint();
    }

    public Object getPanelLog() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public enum ReaderType {
        MASUK,
        KELUAR
    }
    private void loadPorts() {

        cbPortKeluar.removeAllItems();
        cbPortMasuk.removeAllItems();

        List<String> detectedPorts = new ArrayList<>();

        for (SerialPort port : SerialPort.getCommPorts()) {

            String desc = port.getPortDescription();
            String manufacturer = port.getDescriptivePortName();

            if ((desc != null && desc.toLowerCase().contains("cp210")) ||
                (manufacturer != null && manufacturer.toLowerCase().contains("silicon"))) {

                String portName = port.getSystemPortName();
                detectedPorts.add(portName);

                cbPortKeluar.addItem(portName);
                cbPortMasuk.addItem(portName);
            }
        }

        // AUTO SET DEFAULT
        if (detectedPorts.size() == 1) {

            cbPortKeluar.setSelectedIndex(0);
            cbPortMasuk.setSelectedIndex(0);

        } else if (detectedPorts.size() >= 2) {

            cbPortKeluar.setSelectedIndex(0);
            cbPortMasuk.setSelectedIndex(1);
        }
    }
    
    private void validatePortSelection() {

        if (adjustingPort) return;

        int totalPort = cbPortKeluar.getItemCount();

        if (totalPort <= 1) return;

        String portMasuk = (String) cbPortKeluar.getSelectedItem();
        String portKeluar = (String) cbPortMasuk.getSelectedItem();

        if (portMasuk != null && portKeluar != null &&
            portMasuk.equals(portKeluar)) {

            adjustingPort = true;

            for (int i = 0; i < cbPortMasuk.getItemCount(); i++) {

                String otherPort = cbPortMasuk.getItemAt(i);

                if (!otherPort.equals(portMasuk)) {
                    cbPortMasuk.setSelectedItem(otherPort);
                    break;
                }
            }

            adjustingPort = false;
        }
    }
    
    // ================== MASUK ==================

    public void startMasuk() {

        String port = (String) cbPortKeluar.getSelectedItem();
        if (port == null) return;
        updateScanUI(ReaderType.MASUK, true);
        manager.startMasukRealtime(port, (epc, rssi) -> {
            panelMasuk.onTagDetected(epc, rssi);
        });
    }

    public void stopMasuk() {
        manager.stopMasuk();
        updateScanUI(ReaderType.MASUK, false);
    }


    // ================== KELUAR ==================

    public void startKeluar() {

        String port = (String) cbPortMasuk.getSelectedItem();
        if (port == null) return;
        
        int totalPort = cbPortKeluar.getItemCount();

        if (totalPort <= 1) {
            updateScanUI(ReaderType.MASUK, true);
        } else {
            updateScanUI(ReaderType.KELUAR, true);
        }
        
        

        manager.startKeluarRealtime(port, (epc, rssi) -> {
            panelKeluar.onTagDetected(epc, rssi);
        });
    }

    public void stopKeluar() {
        manager.stopKeluar();
        int totalPort = cbPortKeluar.getItemCount();

        if (totalPort <= 1) {
            updateScanUI(ReaderType.MASUK, false);
        } else {
            updateScanUI(ReaderType.KELUAR, false);
        }
    }

    public void startValidasiScan() {

        String port = (String) cbPortMasuk.getSelectedItem();
        if (port == null) return;

        updateScanUI(ReaderType.MASUK, true);

        manager.startMasukRealtime(port,
            (epc, rssi) -> panelSinkronasiData.onTagDetected(epc, rssi)
        );
    }

    public void stopValidasiScan() {

        manager.stopMasuk();
        updateScanUI(ReaderType.MASUK, false);
    }
    
    public void refreshLogLinen() {
        if (panelLog != null) {
            panelLog.loadData();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelUtama = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelReaderKeluar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbPortKeluar = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        panelReader = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbPortMasuk = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        lblStatus12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        tabbedPaneUtama = new silaris_client.tabbed.TabbedPaneCustom();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/silaris_client_assets/logo.png"))); // NOI18N

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        panelReaderKeluar.setOpaque(false);
        panelReaderKeluar.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Reader Masuk : ");
        panelReaderKeluar.add(jLabel2, new java.awt.GridBagConstraints());

        jLabel3.setText("Port ");
        panelReaderKeluar.add(jLabel3, new java.awt.GridBagConstraints());

        panelReaderKeluar.add(cbPortKeluar, new java.awt.GridBagConstraints());

        jLabel4.setText(" Status : ");
        panelReaderKeluar.add(jLabel4, new java.awt.GridBagConstraints());

        lblStatus.setText("jLabel5");
        panelReaderKeluar.add(lblStatus, new java.awt.GridBagConstraints());

        jPanel1.add(panelReaderKeluar, java.awt.BorderLayout.CENTER);

        panelReader.setOpaque(false);
        panelReader.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText("Reader Keluar : ");
        panelReader.add(jLabel5, new java.awt.GridBagConstraints());

        jLabel6.setText("Port ");
        panelReader.add(jLabel6, new java.awt.GridBagConstraints());

        panelReader.add(cbPortMasuk, new java.awt.GridBagConstraints());

        jLabel7.setText(" Status : ");
        panelReader.add(jLabel7, new java.awt.GridBagConstraints());

        lblStatus12.setText("jLabel5");
        panelReader.add(lblStatus12, new java.awt.GridBagConstraints());

        jPanel1.add(panelReader, java.awt.BorderLayout.PAGE_START);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/silaris_client_assets/Refresh_2.png"))); // NOI18N
        jLabel8.setToolTipText("Load Ports");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel8, java.awt.BorderLayout.CENTER);

        lbUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbUser.setText("jLabel2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 325, Short.MAX_VALUE)
                .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout PanelUtamaLayout = new javax.swing.GroupLayout(PanelUtama);
        PanelUtama.setLayout(PanelUtamaLayout);
        PanelUtamaLayout.setHorizontalGroup(
            PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabbedPaneUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelUtamaLayout.setVerticalGroup(
            PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUtamaLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabbedPaneUtama, javax.swing.GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        loadPorts();
    }//GEN-LAST:event_jLabel8MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelUtama;
    private javax.swing.JComboBox<String> cbPortKeluar;
    private javax.swing.JComboBox<String> cbPortMasuk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbUser;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatus12;
    private javax.swing.JPanel panelReader;
    private javax.swing.JPanel panelReaderKeluar;
    private silaris_client.tabbed.TabbedPaneCustom tabbedPaneUtama;
    // End of variables declaration//GEN-END:variables
}
