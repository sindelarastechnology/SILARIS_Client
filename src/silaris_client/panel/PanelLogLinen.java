/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package silaris_client.panel;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import silaris_client.Main;
import silaris_client.dao.DetailLogDAO;
import silaris_client.dao.LogLinenDAO;
import silaris_client.model.DetailLog;
import silaris_client.model.LogLinen;
public class PanelLogLinen extends javax.swing.JPanel {

    private Main main;
    DefaultTableModel tabel1, tabel2, tabel3;
    DetailLogDAO detailLogDAO = new DetailLogDAO();
    
    String detailLogSelected;
    
    public PanelLogLinen(Main main) {
        this.main = main;
        initComponents();
        
        tabel3 = new DefaultTableModel(new Object[]{"No","ID Log","Tanggal","Petugas","Ruangan","Total Linen","Kategori"},0);
        tabel1 = new DefaultTableModel(new Object[]{"No","ID Log","Tanggal","Petugas","Ruangan","Total Linen","Kategori"},0);
        tabel2 = new DefaultTableModel(new Object[]{"No","ID Linen","EPC","Kategori","Nama Linen","Total Cuci","Keterangan"},0);
        tblLogLinenMasuk.setModel(tabel1);
        tblDetailLog.setModel(tabel2);
        tblLogLinenKeluar.setModel(tabel3);
        
        tblLogLinenMasuk.setDefaultEditor(Object.class, null);
        tblLogLinenKeluar.setDefaultEditor(Object.class, null);
        tblDetailLog.setDefaultEditor(Object.class, null);
        
        //Log Linen Masuk
        tblLogLinenMasuk.getColumnModel().getColumn(1).setMinWidth(0);
        tblLogLinenMasuk.getColumnModel().getColumn(1).setMaxWidth(0);
        tblLogLinenMasuk.getColumnModel().getColumn(1).setWidth(0);
        
        //Log Linen Keluar
        tblLogLinenKeluar.getColumnModel().getColumn(1).setMinWidth(0);
        tblLogLinenKeluar.getColumnModel().getColumn(1).setMaxWidth(0);
        tblLogLinenKeluar.getColumnModel().getColumn(1).setWidth(0);

        //Log Linen Detail
        tblDetailLog.getColumnModel().getColumn(1).setMinWidth(0);
        tblDetailLog.getColumnModel().getColumn(1).setMaxWidth(0);
        tblDetailLog.getColumnModel().getColumn(1).setWidth(0);

        
        tblLogLinenMasuk.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting()) {

                tblLogLinenKeluar.clearSelection();

                int x = tblLogLinenMasuk.getSelectedRow();
                if (x >= 0) {
                    detailLogSelected = tabel1.getValueAt(x,1).toString();
                    loadLinen();
                }
            }
        });

        tblLogLinenKeluar.getSelectionModel().addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting()) {

                tblLogLinenMasuk.clearSelection();

                int x = tblLogLinenKeluar.getSelectedRow();
                if (x >= 0) {
                    detailLogSelected = tabel3.getValueAt(x,1).toString();
                    loadLinen();
                }
            }
        });

        
         loadData();
         setupTableColumnWidth();
    }
    
    private void setupTableColumnWidth() {
        //Tabel Linen Masuk
        TableColumnModel columnModel = tblLogLinenMasuk.getColumnModel();
        // Kolom No
        TableColumn colNo = columnModel.getColumn(0);
        colNo.setMinWidth(30);
        colNo.setPreferredWidth(40);
        colNo.setMaxWidth(50);
        // Kolom Total Linen (diperkecil)
        TableColumn colTotalLinen = columnModel.getColumn(5);
        colTotalLinen.setMinWidth(65);
        colTotalLinen.setPreferredWidth(75);
        colTotalLinen.setMaxWidth(85);
        
        //Tabel Linen Keluar
        TableColumnModel columnModel2 = tblLogLinenKeluar.getColumnModel();

        // Kolom No
        TableColumn colNo2 = columnModel2.getColumn(0);
        colNo2.setMinWidth(30);
        colNo2.setPreferredWidth(40);
        colNo2.setMaxWidth(50);

        // Kolom Total Linen
        TableColumn colTotalLinen2 = columnModel2.getColumn(5);
        colTotalLinen2.setMinWidth(65);
        colTotalLinen2.setPreferredWidth(75);
        colTotalLinen2.setMaxWidth(85);
        
        //Tabel Detail
        TableColumnModel columnModel3 = tblDetailLog.getColumnModel();

        // Kolom No
        TableColumn colNo3 = columnModel3.getColumn(0);
        colNo3.setMinWidth(30);
        colNo3.setPreferredWidth(40);
        colNo3.setMaxWidth(50);

        // Kolom Total Cuci
        TableColumn colTotalCuci3 = columnModel3.getColumn(5);
        colTotalCuci3.setMinWidth(65);
        colTotalCuci3.setPreferredWidth(75);
        colTotalCuci3.setMaxWidth(85);
    }
 
    public void loadDataMasuk() {

        LogLinenDAO dao = new LogLinenDAO();
        List<LogLinen> list = dao.getAllLogMasuk();

        DefaultTableModel model =
            (DefaultTableModel) tblLogLinenMasuk.getModel();

        model.setRowCount(0);
        int no = 1;
        for (LogLinen log : list) {
            
            model.addRow(new Object[]{
                no++,
                log.getIdLog(),
                log.getTanggal(),
                log.getPetugas(),
                log.getRuangan(),
                log.getTotalLinen(),
                log.getKategori()
            });
        }
    }
    public void loadDataKeluar() {

        LogLinenDAO dao = new LogLinenDAO();
        List<LogLinen> list = dao.getAllLogKeluar();

        DefaultTableModel model =
            (DefaultTableModel) tblLogLinenKeluar.getModel();

        model.setRowCount(0);
        int no = 1;
        for (LogLinen log : list) {
            
            model.addRow(new Object[]{
                no++,
                log.getIdLog(),
                log.getTanggal(),
                log.getPetugas(),
                log.getRuangan(),
                log.getTotalLinen(),
                log.getKategori()
            });
        }
    }
    
    public void loadData(){
        loadDataMasuk();
        loadDataKeluar();
    }
    
    private void loadLinen() {
        try {
            tabel2.setRowCount(0);
            int no = 1;
            for (DetailLog n : detailLogDAO.getByIdLog(detailLogSelected)) {tabel2.addRow(new Object[]{no++,n.idLinenLog,n.epcLog,n.kategoriLog, n.namaLinenLog, n.jumlahCuciLog, n.keteranganLog});
            }
        } catch (Exception e) { e.printStackTrace(); }
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
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLogLinenMasuk = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLogLinenKeluar = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetailLog = new javax.swing.JTable();

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel8.setLayout(new java.awt.GridLayout(2, 1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 102));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Log Linen Masuk");
        jPanel4.add(jLabel1, new java.awt.GridBagConstraints());

        tblLogLinenMasuk.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblLogLinenMasuk);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel9);

        jPanel11.setBackground(new java.awt.Color(255, 204, 204));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Log Linen Keluar");
        jPanel11.add(jLabel3, new java.awt.GridBagConstraints());

        tblLogLinenKeluar.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblLogLinenKeluar);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel10);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);

        jPanel5.setBackground(new java.awt.Color(153, 255, 102));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Detail Log");
        jPanel5.add(jLabel2, new java.awt.GridBagConstraints());

        jPanel7.setLayout(new java.awt.BorderLayout());

        tblDetailLog.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDetailLog);

        jPanel7.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblDetailLog;
    private javax.swing.JTable tblLogLinenKeluar;
    private javax.swing.JTable tblLogLinenMasuk;
    // End of variables declaration//GEN-END:variables
}
