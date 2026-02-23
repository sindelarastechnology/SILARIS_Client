/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silaris_client.panel;

import java.awt.*;
import java.util.*;
import javax.swing.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import silaris_client.config.Rmt01ReaderService;
import silaris_client.Main;

public class PanelSinkronasiData extends javax.swing.JPanel{
    private Main main;
    DefaultTableModel tabel1, modelC, modelTidakTerdaftar;
    private final Set<String> databaseTags = new HashSet<>();
    private final Map<String, Integer> mapRow = new HashMap<>();
    private final Map<String, Long> bufferDetectedTags = new HashMap<>();
    private final Map<String, Long> detectedTags = new HashMap<>();
    private final Set<String> validatedTags = new HashSet<>();
    private static final long TIMEOUT = 500; // 1.5 detik
    private Timer refreshTimer;
    
    public PanelSinkronasiData(Main main) {
        this.main = main;
        initComponents();
        refreshTimer = new Timer(300, e -> applyBufferedTags());
        refreshTimer.setRepeats(true);
        
        tblLinen.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){

            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column){

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String epc = table.getValueAt(row, 1).toString();

                if(validatedTags.contains(epc)){
                    c.setBackground(new Color(144,238,144)); // hijau muda
                }else{
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });
        modelC = new DefaultTableModel(new Object[]{"No","EPC","Ada/Tidak"},0);
        tabel1 = new DefaultTableModel(new Object[]{"No","EPC","Kategori","Nama Linen"},0);
        modelTidakTerdaftar = new DefaultTableModel(new Object[]{"No","EPC"},0);
        tblCek.setModel(modelC);
        tblLinen.setModel(tabel1);
        tblTidakTerdaftar.setModel(modelTidakTerdaftar);

        btnReset.setEnabled(false);
        btnStop.setEnabled(false);
        
        setupTableColumnWidth();
    }
    
    private void setupTableColumnWidth() {
        TableColumnModel columnModel = tblLinen.getColumnModel();

        TableColumn colNo = columnModel.getColumn(0);
        colNo.setMinWidth(30);
        colNo.setPreferredWidth(40);
        colNo.setMaxWidth(50);
        
        TableColumnModel columnModel2 = tblTidakTerdaftar.getColumnModel();

        TableColumn clNo = columnModel2.getColumn(0);
        clNo.setMinWidth(30);
        clNo.setPreferredWidth(40);
        clNo.setMaxWidth(50);
        
        TableColumnModel columnModel3 = tblCek.getColumnModel();

        TableColumn clNo3 = columnModel3.getColumn(0);
        clNo3.setMinWidth(30);
        clNo3.setPreferredWidth(40);
        clNo3.setMaxWidth(50);
    }
    private void tampilkanLinen(org.json.JSONArray arr){

        try{

            databaseTags.clear();
            detectedTags.clear();
            mapRow.clear();

            tabel1.setRowCount(0);
            modelC.setRowCount(0);
            modelTidakTerdaftar.setRowCount(0);

            org.json.JSONObject obj = arr.getJSONObject(0);
            org.json.JSONArray linen = obj.getJSONArray("linen_dikirim");

            int no = 1;

            for(int i=0;i<linen.length();i++){

                org.json.JSONObject l = linen.getJSONObject(i);
                String epc = l.getString("epc");

                databaseTags.add(epc);

                tabel1.addRow(new Object[]{
                    no++,
                    epc,
                    l.getString("kategori"),
                    l.getString("nama_linen"),
                });

                mapRow.put(epc, tabel1.getRowCount() - 1);
            }

            tblLinen.repaint();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void onTagDetected(String epc, int rssi){

        long now = System.currentTimeMillis();

        if(rssi > -70){
            bufferDetectedTags.put(epc, now);

            // Jika epc ada di database, tandai sebagai tervalidasi permanen
            if(databaseTags.contains(epc)){
                validatedTags.add(epc);
            }
        }
    }
    
    

    private void applyBufferedTags(){

        long now = System.currentTimeMillis();

        // copy buffer ke detected
        for(Map.Entry<String, Long> entry : bufferDetectedTags.entrySet()){
            detectedTags.put(entry.getKey(), entry.getValue());
        }

        // hapus yang sudah timeout
        detectedTags.entrySet().removeIf(entry ->
                now - entry.getValue() > TIMEOUT
        );

        refreshValidation();
    }
    
    private void refreshValidation(){

        if(databaseTags.isEmpty()) return;
        modelC.setRowCount(0);
        modelTidakTerdaftar.setRowCount(0);

        int noCek = 1;
        int noTidak = 1;

        for(String epc : detectedTags.keySet()){

            boolean ada = databaseTags.contains(epc);

            // TABEL CEK (KIRI)
            modelC.addRow(new Object[]{
                noCek++,
                epc,
                ada ? "ADA" : "TIDAK"
            });

            // Jika tidak ada di database -> masuk kanan
            if(!ada){
                modelTidakTerdaftar.addRow(new Object[]{
                    noTidak++,
                    epc
                });
            }
        }

        tblLinen.repaint();
    }
    
    public void startScan(){

        main.startMasuk(); // atau method khusus validasi
        refreshTimer.start();
    }

    public void stopScan(){

        main.stopMasuk();
        refreshTimer.stop();

        bufferDetectedTags.clear();
        detectedTags.clear();

        refreshValidation();
    }
    
    public void refreshAll(){
        modelC.setRowCount(0);
        modelTidakTerdaftar.setRowCount(0);
        validatedTags.clear();
        tblLinen.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCek = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        txtkode = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        btnGetData = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLinen = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTidakTerdaftar = new javax.swing.JTable();

        jPanel1.setLayout(new java.awt.GridLayout(1, 3));

        jPanel2.setBackground(new java.awt.Color(102, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 102));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Cek Linen");
        jPanel9.add(jLabel3, new java.awt.GridBagConstraints());

        jPanel10.setLayout(new java.awt.BorderLayout());

        tblCek.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        tblCek.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblCek);

        jPanel10.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel11.setLayout(new java.awt.GridLayout(3, 1));

        jPanel12.setBackground(new java.awt.Color(255, 204, 51));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Scan Linen");
        jPanel12.add(jLabel4, new java.awt.GridBagConstraints());

        jPanel11.add(jPanel12);

        jPanel13.setBackground(new java.awt.Color(102, 255, 255));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        btnStart.setBackground(new java.awt.Color(153, 255, 153));
        btnStart.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnStart.setText("Start Scan");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        jPanel13.add(btnStart, new java.awt.GridBagConstraints());

        btnStop.setBackground(new java.awt.Color(255, 102, 102));
        btnStop.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnStop.setText("Stop Scan");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        jPanel13.add(btnStop, new java.awt.GridBagConstraints());

        btnReset.setBackground(new java.awt.Color(51, 153, 255));
        btnReset.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnReset.setText("Reset Tabel");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel13.add(btnReset, new java.awt.GridBagConstraints());

        jPanel11.add(jPanel13);

        jPanel19.setBackground(new java.awt.Color(102, 255, 255));
        jPanel19.setLayout(new java.awt.GridBagLayout());

        btnSimpan.setBackground(new java.awt.Color(51, 255, 255));
        btnSimpan.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnSimpan.setText("Simpan Data");
        jPanel19.add(btnSimpan, new java.awt.GridBagConstraints());

        jPanel11.add(jPanel19);

        jPanel14.setBackground(new java.awt.Color(102, 255, 255));
        jPanel14.setLayout(new java.awt.GridLayout(3, 1));

        jPanel15.setBackground(new java.awt.Color(255, 255, 102));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Verifikasi Linen");
        jPanel15.add(jLabel5, new java.awt.GridBagConstraints());

        jPanel14.add(jPanel15);

        jPanel16.setLayout(new java.awt.GridLayout(1, 2));

        jPanel20.setBackground(new java.awt.Color(102, 255, 255));
        jPanel20.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel6.setText("Kode Verifikasi:");
        jPanel20.add(jLabel6, new java.awt.GridBagConstraints());

        jPanel16.add(jPanel20);

        jPanel21.setBackground(new java.awt.Color(102, 255, 255));

        txtkode.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtkode, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtkode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel16.add(jPanel21);

        jPanel14.add(jPanel16);

        jPanel17.setLayout(new java.awt.GridLayout(1, 2));

        jPanel18.setBackground(new java.awt.Color(102, 255, 255));
        jPanel18.setLayout(new java.awt.GridBagLayout());

        btnGetData.setBackground(new java.awt.Color(153, 255, 153));
        btnGetData.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnGetData.setText("Get Data");
        btnGetData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetDataActionPerformed(evt);
            }
        });
        jPanel18.add(btnGetData, new java.awt.GridBagConstraints());

        jPanel17.add(jPanel18);

        jPanel14.add(jPanel17);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(102, 255, 255));

        jPanel5.setBackground(new java.awt.Color(153, 255, 102));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Linen Terdaftar");
        jPanel5.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel7.setLayout(new java.awt.BorderLayout());

        tblLinen.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        tblLinen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblLinen);

        jPanel7.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(102, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 153, 153));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Linen Tidak Terdaftar");
        jPanel6.add(jLabel2, new java.awt.GridBagConstraints());

        jPanel8.setLayout(new java.awt.BorderLayout());

        tblTidakTerdaftar.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        tblTidakTerdaftar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblTidakTerdaftar);

        jPanel8.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGetDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetDataActionPerformed
        tabel1.setRowCount(0);
        String kode = txtkode.getText();

        if(kode.isEmpty()){
            javax.swing.JOptionPane.showMessageDialog(this,"Kode verifikasi kosong");
            return;
        }

        org.json.JSONArray data =
        silaris_client.service.PengirimanService.getLinenByKode(kode);

        if(data==null || data.length()==0){
            javax.swing.JOptionPane.showMessageDialog(this,
            "Kode tidak ditemukan / bukan milik RS anda");
            return;
        }

        tampilkanLinen(data);
        
    }//GEN-LAST:event_btnGetDataActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        refreshAll();
        main.startValidasiScan();
        refreshTimer.start();
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        main.stopValidasiScan();
        refreshTimer.stop();
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        btnReset.setEnabled(true);
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
       refreshAll();
       btnReset.setEnabled(false);
    }//GEN-LAST:event_btnResetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGetData;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblCek;
    private javax.swing.JTable tblLinen;
    private javax.swing.JTable tblTidakTerdaftar;
    private javax.swing.JTextField txtkode;
    // End of variables declaration//GEN-END:variables
}
