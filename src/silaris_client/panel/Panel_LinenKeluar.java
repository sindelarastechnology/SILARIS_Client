/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package silaris_client.panel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import silaris_client.Main;
import silaris_client.config.SQLiteConfig;
import silaris_client.model.Ruangan;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import silaris_client.dao.LogLinenDAO;
import silaris_client.model.LogLinen;


// Buat form, selain ruangan, nama petugas, tanggal, >> simpan di db linen keluar sqlite
// buat panel history linen keluar, 
public class Panel_LinenKeluar extends javax.swing.JPanel {

    public static Panel_LinenKeluar instance;
    DefaultTableModel tabel1, tabel2;
    private Main main;
    public Panel_LinenKeluar(Main main) {
        this.main=main;
        initComponents();
        setTanggalOtomatis();
        
        tabel1 = new DefaultTableModel(new Object[]{"No","EPC"},0);
        tabel2 = new DefaultTableModel(new Object[]{"No","EPC","Kategori","Nama Linen"},0);
        tblCekLinen.setModel(tabel1);
        tblLinenTerdaftar.setModel(tabel2);
        
        instance = this;  
        btnStop.setEnabled(false);
        btnReset.setEnabled(false);
        loadRuangan(); 
    }
    
    public void addEPC(String epc) {
        SwingUtilities.invokeLater(() -> {
            tabel1.addRow(new Object[]{
                    tabel1.getRowCount() + 1,
                    epc
            });
        });
    }
    
    public void loadRuangan() {
        try {
            cbRuangan.removeAllItems();

            String sql = "SELECT * FROM ruangan ORDER BY kode_ruangan ASC";
            Connection c = SQLiteConfig.connect();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);

           while (r.next()) {

               Ruangan ruangan = new Ruangan();
                ruangan.id = r.getString("id");
                ruangan.kodeRuangan = r.getString("kode_ruangan");
                ruangan.namaRuangan = r.getString("nama_ruangan");
                ruangan.keterangan = r.getString("keterangan");

                cbRuangan.addItem(ruangan);
            }
                cbRuangan.revalidate();
                cbRuangan.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load ruangan: " + e.getMessage());
       }
    } 
    
     public static void refreshCombo() {
        if (instance != null) {
            instance.loadRuangan();
        }
    }
     
     public void resetTable() {
        tabel1.setRowCount(0);
        tabel2.setRowCount(0);
    }
    
     private void setTanggalOtomatis() {
        DateTimeFormatter format =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        txtTanggal.setText(LocalDateTime.now().format(format));
        txtTanggal.setEditable(false);
    }
     
    private void resetForm() {
        txtPetugas.setText("");
//        cbRuangan.setSelectedIndex(-1);
        setTanggalOtomatis();
    }
    
     private String generateId() {
        return UUID.randomUUID().toString(); 
    }
    
    private void proses() {
        Ruangan ruangan = (Ruangan) cbRuangan.getSelectedItem();
        String petugas = txtPetugas.getText().trim();
        String tanggal = txtTanggal.getText();

        // ================= VALIDASI =================
        if (ruangan == null) {
            JOptionPane.showMessageDialog(this, "Silakan pilih ruangan!");
            return;
        }

        if (petugas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Petugas tidak boleh kosong!");
            return;
        }

        try {
            // ================= SIMPAN KE DATABASE =================
            String id = generateId();

            LogLinen log = new LogLinen();
            log.setIdLog(id);
            log.setTanggal(tanggal);
            log.setPetugas(petugas);
            log.setRuangan(ruangan.getNama()); 
            // atau ruangan.getKodeRuangan()

            LogLinenDAO dao = new LogLinenDAO();
            dao.insert(log);

            JOptionPane.showMessageDialog(this, "Pengambilan linen berhasil disimpan!");

            if (main != null) {
                main.refreshLogLinen();
            }
            resetForm();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data!");
        }
     }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbRuangan = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtPetugas = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        btnProses = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCekLinen = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLinenTerdaftar = new javax.swing.JTable();

        jPanel1.setLayout(new java.awt.GridLayout(1, 3));

        jPanel2.setBackground(new java.awt.Color(102, 255, 255));

        jPanel10.setLayout(new java.awt.GridLayout(3, 1));

        jPanel11.setBackground(new java.awt.Color(255, 153, 51));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Scan Linen");
        jPanel11.add(jLabel5, new java.awt.GridBagConstraints());

        jPanel10.add(jPanel11);

        jPanel12.setBackground(new java.awt.Color(255, 255, 204));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        btnStart.setBackground(new java.awt.Color(153, 255, 153));
        btnStart.setText("Start Scan");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        jPanel12.add(btnStart, new java.awt.GridBagConstraints());

        btnStop.setBackground(new java.awt.Color(255, 102, 102));
        btnStop.setText("Stop Scan");
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        jPanel12.add(btnStop, new java.awt.GridBagConstraints());

        btnReset.setBackground(new java.awt.Color(51, 153, 255));
        btnReset.setText("Reset Tabel");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel12.add(btnReset, new java.awt.GridBagConstraints());

        jPanel10.add(jPanel12);

        jPanel13.setBackground(new java.awt.Color(255, 255, 204));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        jButton4.setBackground(new java.awt.Color(51, 255, 255));
        jButton4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jButton4.setText("Simpan");
        jPanel13.add(jButton4, new java.awt.GridBagConstraints());

        jPanel10.add(jPanel13);

        jPanel15.setBackground(new java.awt.Color(255, 255, 153));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Form Pengambilan Linen");
        jPanel15.add(jLabel8, new java.awt.GridBagConstraints());

        jPanel14.setBackground(new java.awt.Color(255, 255, 204));
        jPanel14.setLayout(new java.awt.GridLayout(3, 2));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setText("Pilih Ruangan:");
        jPanel14.add(jLabel4);

        jPanel14.add(cbRuangan);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel6.setText("Petugas:");
        jPanel14.add(jLabel6);
        jPanel14.add(txtPetugas);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel7.setText("Tanggal:");
        jPanel14.add(jLabel7);
        jPanel14.add(txtTanggal);

        jPanel9.setBackground(new java.awt.Color(255, 255, 204));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        btnProses.setBackground(new java.awt.Color(51, 255, 255));
        btnProses.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnProses.setText("Proses");
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });
        jPanel9.add(btnProses, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(194, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        jPanel5.setBackground(new java.awt.Color(255, 255, 102));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Cek Linen");
        jPanel5.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel6.setLayout(new java.awt.BorderLayout());

        tblCekLinen.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCekLinen);

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        jPanel7.setBackground(new java.awt.Color(153, 255, 102));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Linen Terdaftar");
        jPanel7.add(jLabel2, new java.awt.GridBagConstraints());

        jPanel8.setLayout(new java.awt.BorderLayout());

        tblLinenTerdaftar.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblLinenTerdaftar);

        jPanel8.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        resetTable();
        main.startKeluar();
        btnReset.setEnabled(false);
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        main.stopKeluar();
        btnReset.setEnabled(true);
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetTable();
        btnReset.setEnabled(false);
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesActionPerformed
        proses();
    }//GEN-LAST:event_btnProsesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProses;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JComboBox<Ruangan> cbRuangan;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblCekLinen;
    private javax.swing.JTable tblLinenTerdaftar;
    private javax.swing.JTextField txtPetugas;
    private javax.swing.JTextField txtTanggal;
    // End of variables declaration//GEN-END:variables
}
