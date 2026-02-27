/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silaris_client.menu;

import java.awt.Font;
import silaris_client.Main;
import silaris_client.ReaderConfig.ReaderManager;
import silaris_client.panel.PanelLogLinen;
import silaris_client.panel.PanelReaderTest;
import silaris_client.panel.Panel_LinenKeluar;
import silaris_client.panel.Panel_LinenMasuk;

/**
 *
 * @author HP
 */
public class MenuLaundry extends javax.swing.JPanel {

    private Main main;
    private PanelLogLinen panelLogLinen;
    private Panel_LinenMasuk panel_LinenMasuk;
    private Panel_LinenKeluar panel_LinenKeluar;
    public MenuLaundry(Main main, Panel_LinenKeluar panel_LinenKeluar, Panel_LinenMasuk panel_LinenMasuk) {
        this.main=main;
        initComponents();
        
        panelLogLinen = new PanelLogLinen(main);
//        panel_LinenMasuk = new Panel_LinenMasuk(main);
//        panel_LinenKeluar = new Panel_LinenKeluar(main);
        
        tabbedMenuLaundry.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tabbedMenuLaundry.addTab("Linen Masuk ", panel_LinenMasuk);
        tabbedMenuLaundry.addTab("Linen Keluar ", panel_LinenKeluar);
        tabbedMenuLaundry.addTab("History Linen ", panelLogLinen);
        
        
        tabbedMenuLaundry.addChangeListener(e -> {
            int i = tabbedMenuLaundry.getSelectedIndex();
            String title = tabbedMenuLaundry.getTitleAt(i);

            if (title.equals("History Linen ")) {
                loadData();
            }
           
        });
    }

    public void loadData(){
        panelLogLinen.loadData();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedMenuLaundry = new silaris_client.tabbed.TabbedPaneCustom();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedMenuLaundry, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedMenuLaundry, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private silaris_client.tabbed.TabbedPaneCustom tabbedMenuLaundry;
    // End of variables declaration//GEN-END:variables
}
