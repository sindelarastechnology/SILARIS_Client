/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package silaris_client.panel;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import silaris_client.Main;
import silaris_client.model.Linen;
import silaris_client.dao.LinenDAO;


/**
 *
 * @author HP
 */
public class PanelDataLinen extends javax.swing.JPanel {
    private Main main;
    DefaultTableModel modelDL, modelLC, modelLP;
    LinenDAO linenDAO = new LinenDAO();
    
    String penerimaanSelected;
    public PanelDataLinen(Main main) {
        this.main = main;
        initComponents();
        
        modelDL = new DefaultTableModel(new Object[]{"No","EPC","Kategori", "Nama Linen","Lokasi","Total Cuci"},0);
        modelLC = new DefaultTableModel(new Object[]{"No","EPC","Kategori", "Nama Linen","Lokasi"},0);
        modelLP = new DefaultTableModel(new Object[]{"No","EPC","Kategori", "Nama Linen","Lokasi"},0);
        
        tblDataLinen.setModel(modelDL);
        tblLinenCuci.setModel(modelLC);
        tblLinenPakai.setModel(modelLP);
        
        loadLinen();
        
        
        setupTableColumnWidth();
         
    }
    
    public void loadLinen(){
        loadDataLinen();
        loadLinenCuci();
        loadLinenPakai();
    }
    
    
        private void setupTableColumnWidth() {
        TableColumnModel columnModel = tblDataLinen.getColumnModel();
        TableColumn colNo = columnModel.getColumn(0);
        colNo.setMinWidth(30);
        colNo.setPreferredWidth(40);
        colNo.setMaxWidth(50);
        
        TableColumnModel columnModel2 = tblLinenCuci.getColumnModel();
        TableColumn colNo2 = columnModel2.getColumn(0);
        colNo2.setMinWidth(30);
        colNo2.setPreferredWidth(40);
        colNo2.setMaxWidth(50);
        
        TableColumnModel columnModel3 = tblLinenPakai.getColumnModel();
        TableColumn colNo3 = columnModel3.getColumn(0);
        colNo3.setMinWidth(30);
        colNo3.setPreferredWidth(40);
        colNo3.setMaxWidth(50);
    }

    private void loadDataLinen() {
        try {
            modelDL.setRowCount(0);
            int no = 1;
            for (Linen n : linenDAO.getAll()) {
                // Hitung hanya yang statusnya "Dipakai"
                int jumlahDipakai = 0;
                if (n.ketLin != null && n.ketLin.equalsIgnoreCase("Dipakai")) {
                    jumlahDipakai = 1;
                }
                modelDL.addRow(new Object[]{no++,n.epc,n.kategoriLin,n.namaLin,n.lokasiLin,n.jumlahCuci});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    
     private void loadLinenCuci() {
        try {
            modelLC.setRowCount(0);
            int no = 1;
            for (Linen n : linenDAO.getAllDicuci()) {
                modelLC.addRow(new Object[]{no++,n.epc,n.kategoriLin,n.namaLin,n.lokasiLin});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
     
    private void loadLinenPakai() {
        try {
            modelLP.setRowCount(0);
            int no = 1;
            for (Linen n : linenDAO.getAllDipakai()) {
                modelLP.addRow(new Object[]{no++,n.epc,n.kategoriLin,n.namaLin,n.lokasiLin});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataLinen = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLinenCuci = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLinenPakai = new javax.swing.JTable();

        jPanel1.setLayout(new java.awt.GridLayout(1, 3));

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Data Semua Linen");
        jPanel2.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel2);

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        tblDataLinen.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        tblDataLinen.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDataLinen);

        jPanel5.add(jScrollPane1);

        jPanel6.setLayout(new java.awt.GridLayout(1, 2));

        jPanel4.setBackground(new java.awt.Color(153, 255, 153));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Linen Dicuci");
        jPanel4.add(jLabel2, new java.awt.GridBagConstraints());

        tblLinenCuci.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        tblLinenCuci.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblLinenCuci);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel7);

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Linen Dipakai");
        jPanel3.add(jLabel3, new java.awt.GridBagConstraints());

        tblLinenPakai.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        tblLinenPakai.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblLinenPakai);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel8);

        jPanel5.add(jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblDataLinen;
    private javax.swing.JTable tblLinenCuci;
    private javax.swing.JTable tblLinenPakai;
    // End of variables declaration//GEN-END:variables

}
