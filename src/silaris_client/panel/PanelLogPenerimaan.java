/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silaris_client.panel;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import silaris_client.dao.LinenDAO;
import silaris_client.dao.PenerimaanDAO;
import silaris_client.model.Penerimaan;
import silaris_client.model.Linen;

/**
 *
 * @author HP
 */
public class PanelLogPenerimaan extends javax.swing.JPanel {

    DefaultTableModel modelP, modelD;
    PenerimaanDAO penerimaanDAO = new PenerimaanDAO();
    LinenDAO linenDAO = new LinenDAO();
    
    String penerimaanSelected;
    public PanelLogPenerimaan() {
        initComponents();
        
        modelP = new DefaultTableModel(new Object[]{"No","ID","Tanggal","Petugas", "Jumlah Linen", "Keterangan"},0);
        modelD = new DefaultTableModel(new Object[]{"No","ID","EPC","Kategori", "Nama Linen"},0);
        
        tblPener.setModel(modelP);
        tblDetail.setModel(modelD);
        
        tblPener.getColumnModel().getColumn(1).setMinWidth(0);
        tblPener.getColumnModel().getColumn(1).setMaxWidth(0);
        tblPener.getColumnModel().getColumn(1).setWidth(0);
        
        tblDetail.getColumnModel().getColumn(1).setMinWidth(0);
        tblDetail.getColumnModel().getColumn(1).setMaxWidth(0);
        tblDetail.getColumnModel().getColumn(1).setWidth(0);
        
        tblPener.getSelectionModel().addListSelectionListener(ev -> {
            
            int x = tblPener.getSelectedRow();
            if (x >= 0) {
                penerimaanSelected = modelP.getValueAt(x,1).toString();
                loadLinen();
            }
        });
        
        loadPenerimaan();
        setupTableColumnWidth();
    }
    
    private void setupTableColumnWidth() {
        TableColumnModel columnModel = tblPener.getColumnModel();
        TableColumn colNo = columnModel.getColumn(0);
        colNo.setMinWidth(50);
        colNo.setPreferredWidth(60);
        colNo.setMaxWidth(70);
        
        TableColumnModel columnModel2 = tblDetail.getColumnModel();
        TableColumn colNo2 = columnModel2.getColumn(0);
        colNo2.setMinWidth(50);
        colNo2.setPreferredWidth(60);
        colNo2.setMaxWidth(70);
    }
    
    private void loadPenerimaan() {
        try {
            modelP.setRowCount(0);
            int no = 1;
            for (Penerimaan k : penerimaanDAO.getAll()) {
                modelP.addRow(new Object[]{no++,k.idPen,k.tanggalPen,k.petugasPen,k.totalLin,k.ketPen});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    private void loadLinen() {
        try {
            modelD.setRowCount(0);
            int no = 1;
            for (Linen n : linenDAO.getByIdPenerimaan(penerimaanSelected)) {
                modelD.addRow(new Object[]{no++,n.idLinen,n.epc,n.kategoriLin, n.namaLin});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPener = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetail = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel2.setLayout(new java.awt.BorderLayout());

        tblPener.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblPener);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Penerimaan");
        jPanel3.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2);

        jPanel4.setLayout(new java.awt.BorderLayout());

        tblDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDetail);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(204, 255, 204));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Detail");
        jPanel5.add(jLabel2, new java.awt.GridBagConstraints());

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDetail;
    private javax.swing.JTable tblPener;
    // End of variables declaration//GEN-END:variables
}
