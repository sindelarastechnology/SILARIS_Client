/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silaris_client;

/**
 *
 * @author HP
 */
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;
import org.json.JSONObject;
import silaris_client.session.SessionSQLite;
import silaris_client.service.LoginService;
import silaris_client.Main;
import silaris_client.panel.PanelLogin;

public class SplashScreen extends javax.swing.JFrame {

     private JProgressBar bar;
    private JLabel persen;
    private JLabel loading;
    private JLabel dataSize;

    public SplashScreen() {

        setSize(500,260);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());

        // ================= PANEL UTAMA =================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));

        // ================= LOGO =================
        ImageIcon icon = new ImageIcon(
                getClass().getResource("/silaris_client_assets/logo.png")
        );

        int maxWidth  = 220;
        int maxHeight = 220;

        int imgWidth  = icon.getIconWidth();
        int imgHeight = icon.getIconHeight();

        double ratio = Math.min((double)maxWidth/imgWidth,
                                (double)maxHeight/imgHeight);

        int newW = (int)(imgWidth * ratio);
        int newH = (int)(imgHeight * ratio);

        Image scaled = icon.getImage().getScaledInstance(newW,newH,Image.SCALE_SMOOTH);

        JLabel title = new JLabel(new ImageIcon(scaled));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // ================= NAMA SISTEM =================
        JLabel sub = new JLabel("Sistem Linen Rumah Sakit");
        sub.setFont(new Font("Segoe UI",Font.PLAIN,16));
        sub.setHorizontalAlignment(SwingConstants.CENTER);

        // ================= PANEL ATAS (PUTIH) =================
        JPanel panelAtas = new JPanel();
        panelAtas.setBackground(Color.WHITE);
        panelAtas.setLayout(new BoxLayout(panelAtas, BoxLayout.Y_AXIS));
        panelAtas.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelAtas.add(title);
        panelAtas.add(Box.createVerticalStrut(10));
        panelAtas.add(sub);

         // ================= LOADING TEXT =================
        loading = new JLabel("Memuat sistem...");
        loading.setFont(new Font("Segoe UI",Font.PLAIN,13));

        dataSize = new JLabel("0 MB (0%)");
        dataSize.setFont(new Font("Segoe UI",Font.PLAIN,13));

        persen = new JLabel(""); // tetap ada tapi tidak dipakai
        persen.setFont(new Font("Segoe UI",Font.BOLD,12));

        JPanel panelLoading = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
        panelLoading.setOpaque(false);
        panelLoading.add(loading);
        panelLoading.add(dataSize);

        // ================= PROGRESS =================
        bar = new JProgressBar(0,100);
        bar.setPreferredSize(new Dimension(400,16));
        bar.setBorderPainted(false);

        // ================= PANEL BAWAH (ABU) =================
        JPanel panelBawah = new JPanel();
        panelBawah.setBackground(new Color(240,240,240));
        panelBawah.setLayout(new BoxLayout(panelBawah, BoxLayout.Y_AXIS));
        panelBawah.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
        
        // memperkecil tinggi panel abu-abu
        panelBawah.setPreferredSize(new Dimension(500,70));

        panelLoading.setAlignmentX(Component.CENTER_ALIGNMENT);
        bar.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBawah.add(panelLoading);
        panelBawah.add(Box.createVerticalStrut(8));
        panelBawah.add(bar);

        // ================= GABUNGKAN PANEL =================
        mainPanel.add(panelAtas, BorderLayout.CENTER);
        mainPanel.add(panelBawah, BorderLayout.SOUTH);

        add(mainPanel);

        // THREAD LOADING + CEK SESSION
        new Thread(() -> {
            loading();
            cekSession();
        }).start();
    }

    private void loading(){

        double totalMB = 5.0;
        double currentMB = 0;

        java.util.Random rand = new java.util.Random();

        try{

            while(currentMB < totalMB){

                // seperti download data
                double step = rand.nextDouble() * 0.20;
                currentMB += step;

                if(currentMB > totalMB){
                    currentMB = totalMB;
                }

                int percent = (int)((currentMB / totalMB) * 100);

                double finalMB = currentMB;

                SwingUtilities.invokeLater(() -> {

                    bar.setValue(percent);

                    dataSize.setText(
                            String.format("%.2f MB (%d%%)", finalMB, percent)
                    );

                });

                // delay seperti koneksi internet
                Thread.sleep(100 + rand.nextInt(300));

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

     private void cekSession(){
        try{
            Thread.sleep(500); // animasi splash

            JSONObject session = SessionSQLite.get();

            if(session == null){
                // belum login
                new PanelLogin().setVisible(true);
                this.dispose();
                return;
            }

            // validasi ke supabase
            String email = session.getString("email");
            String password = session.getString("password");

            JSONObject server = LoginService.login(email,password);

            if(server == null){
                // akun tidak ada
                SessionSQLite.clear();
                new PanelLogin().setVisible(true);
                this.dispose();
                return;
            }

            // cek id_rs sama?
            if(server.getString("rs_id")
                    .equals(session.getString("id_rs"))){

                // session valid → dashboard
                new Main().setVisible(true);
                this.dispose();

            }else{
                SessionSQLite.clear();
                new PanelLogin().setVisible(true);
                this.dispose();
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SplashScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
