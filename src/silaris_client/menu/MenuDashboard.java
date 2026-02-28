/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silaris_client.menu;

import java.awt.Frame;
import java.awt.Window;
import javax.swing.SwingUtilities;
import silaris_client.sweetAlert.SweetAlert_dashboard;
import silaris_client.session.SessionSQLite;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;
import silaris_client.Main;
import silaris_client.config.SQLiteConfig;
import silaris_client.dao.DashboardDAO;
import silaris_client.model.Linen;
import silaris_client.dao.LinenDAO;
import java.util.List;
import silaris_client.dao.RuanganDAO;
import silaris_client.model.Ruangan;
/**
 *
 * @author HP
 */
public class MenuDashboard extends javax.swing.JPanel {

    JSONObject ses = SessionSQLite.get();
    
    private boolean alertShown = false;
    private DashboardDAO dashboardDAO = new DashboardDAO();
    private JPanel dashboardContainer;
    private JPanel panelData;
    private JTable table;
    private DefaultTableModel model;
    
    private Main main;
    private MenuDashboard menuDashboard;
    public MenuDashboard(Main main) {
        this.main = main;
        initComponents();
        initDashboardStats();
    }
    
    // ================================
    // METHOD UNTUK DI PANGGIL DARI LUAR
    // ================================
    public void loadCounts() {
        if (dashboardContainer != null) {
            refreshDashboard();
        }
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        if (!alertShown) {
            alertShown = true;

            SwingUtilities.invokeLater(() -> {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window instanceof Frame) {

                   String message = "<html><center>"
                        + "Autentikasi Berhasil<br>"
                        + "Selamat Datang, <b>"
                        + ses.getString("nama_rs")
                        + "</b></center></html>";

                    SweetAlert_dashboard alert = new SweetAlert_dashboard((Frame) window, message);
                    alert.setVisible(true);
                }
            });
        }
    }
    
    // ================================
    // INISIALISASI DASHBOARD
    // ================================
    private void initDashboardStats() {

        this.setBackground(new Color(245, 248, 250));
        this.setLayout(new BorderLayout());

        // ================= CARD CONTAINER =================
        dashboardContainer = new JPanel(new GridLayout(1, 4, 20, 20));
        dashboardContainer.setOpaque(false);
        dashboardContainer.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        refreshDashboard();

        // ================= PANEL TABEL =================
        panelData = new JPanel(new BorderLayout());
        panelData.setBackground(Color.WHITE);
        panelData.setBorder(
                BorderFactory.createEmptyBorder(0, 20, 20, 20));

        model = new DefaultTableModel();
        table = new JTable(model);
        table.setRowHeight(25);

        panelData.add(new JScrollPane(table), BorderLayout.CENTER);

        this.add(dashboardContainer, BorderLayout.NORTH);
        this.add(panelData, BorderLayout.CENTER);
    }

    // ================================
    // REFRESH DATA DASHBOARD
    // ================================
      private void refreshDashboard() {

        dashboardContainer.removeAll();

        int totalLinen = dashboardDAO.getTotalLinen();
        int linenDicuci = dashboardDAO.getTotalDicuci();
        int linenDipakai = dashboardDAO.getTotalDipakai();
        int totalRuangan = dashboardDAO.getTotalRuangan();

        dashboardContainer.add(
            createStatCard("Total Linen",
                String.valueOf(totalLinen),
                new Color(52, 152, 219),
                () -> loadAllLinen())
        );

        dashboardContainer.add(
            createStatCard("Linen Dicuci",
                String.valueOf(linenDicuci),
                new Color(241, 196, 15),
                () -> loadDicuci())
        );

        dashboardContainer.add(
            createStatCard("Linen Dipakai",
                String.valueOf(linenDipakai),
                new Color(46, 204, 113),
                () -> loadDipakai())
        );

        dashboardContainer.add(
            createStatCard("Total Ruangan",
                String.valueOf(totalRuangan),
                new Color(155, 89, 182),
                () -> loadRuangan())
        );

        dashboardContainer.revalidate();
        dashboardContainer.repaint();
    }

    // ================================
    // TEMPLATE CARD MODERN
    // ================================
   private JPanel createStatCard(String title,
                                  String value,
                                  Color color,
                                  Runnable action) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(new Color(130,130,130));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValue.setForeground(color);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        // EVENT KLIK
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.run();
            }
        });

        return card;
    }
       // ================================
    // LOAD DATA TOTAL LINEN
    // ================================
    private void loadAllLinen() {
        try {
            LinenDAO dao = new LinenDAO();
            List<Linen> list = dao.getAll();

            model.setRowCount(0);
            model.setColumnCount(0);

            model.setColumnIdentifiers(
                    new String[]{"EPC","Nama Linen","Kategori","Lokasi","Jumlah Dicuci"}
            );

            for (Linen l : list) {
                model.addRow(new Object[]{
                        l.epc,
                        l.namaLin,
                        l.kategoriLin,
                        l.lokasiLin,
                        l.jumlahCuci
                });
            }

            model.fireTableDataChanged();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
        }
    }
    
    private void loadDicuci() {

        try {
            model.setRowCount(0);
            model.setColumnCount(0);

            model.setColumnIdentifiers(
                    new String[]{"EPC","Nama Linen","Kategori","Lokasi"}
            );

            LinenDAO dao = new LinenDAO();
            List<Linen> list = dao.getAllDicuci();

            for (Linen l : list) {
                model.addRow(new Object[]{
                        l.epc,
                        l.namaLin,
                        l.kategoriLin,
                        l.lokasiLin
                });
            }

            model.fireTableDataChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     private void loadDipakai() {

        try {
            model.setRowCount(0);
            model.setColumnCount(0);

            model.setColumnIdentifiers(
                    new String[]{"EPC","Nama Linen","Kategori","Lokasi"}
            );

            LinenDAO dao = new LinenDAO();
            List<Linen> list = dao.getAllDipakai();

            for (Linen l : list) {
                model.addRow(new Object[]{
                        l.epc,
                        l.namaLin,
                        l.kategoriLin,
                        l.lokasiLin
                });
            }

            model.fireTableDataChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadRuangan() {

        try {
            model.setRowCount(0);
            model.setColumnCount(0);

            model.setColumnIdentifiers(
                    new String[]{"Kode Ruangan","Nama Ruangan","Keterangan"}
            );

            RuanganDAO dao = new RuanganDAO();
            List<Ruangan> list = dao.getAll();

            for (Ruangan r : list) {
                model.addRow(new Object[]{
                        r.kodeRuangan,
                        r.namaRuangan,
                        r.keterangan
                });
            }

            model.fireTableDataChanged();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Gagal load ruangan: " + e.getMessage());
        }
    }

    private void showSuccessAlert(String message) {
        javax.swing.JOptionPane optionPane = new javax.swing.JOptionPane(
                message,
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

        javax.swing.JDialog dialog = optionPane.createDialog("Login Berhasil");
        dialog.setModal(false);
        dialog.setVisible(true);

        new javax.swing.Timer(2000, e -> dialog.dispose()).start();
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 735, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
