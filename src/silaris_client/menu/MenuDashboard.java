/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silaris_client.menu;

import java.awt.Frame;
import java.awt.Window;
import javax.swing.SwingUtilities;
import org.json.JSONObject;
import silaris_client.sweetAlert.SweetAlert_dashboard;
import silaris_client.session.SessionSQLite;

/**
 *
 * @author HP
 */
public class MenuDashboard extends javax.swing.JPanel {

    JSONObject ses = SessionSQLite.get();
    private boolean alertShown = false;

    public MenuDashboard() {
        initComponents();
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
