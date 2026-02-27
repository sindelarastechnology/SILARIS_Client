/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silaris_client.menu;

import java.awt.Font;
import silaris_client.Main;
import silaris_client.panel.PanelSinkronasiData;
import silaris_client.panel.PanelLogPenerimaan;

/**
 *
 * @author HP
 */
public class MenuSetting extends javax.swing.JPanel {

    private Main main;
    private PanelLogPenerimaan panelLogPenerimaan;
    public MenuSetting(PanelSinkronasiData panelSinkronasiData) {
        this.main=main;
        initComponents();
        
        panelLogPenerimaan = new PanelLogPenerimaan(main);
        // set font tab menjadi Times New Roman
        tabbedMenuSetting.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tabbedMenuSetting.addTab("Sync Data ", panelSinkronasiData);
        tabbedMenuSetting.addTab("Log Pengiriman Data ", panelLogPenerimaan);
        
        tabbedMenuSetting.addChangeListener(e -> {
            int i = tabbedMenuSetting.getSelectedIndex();
            String title = tabbedMenuSetting.getTitleAt(i);

            if (title.equals("Log Pengiriman Data ")) {
                loadLog();
            }
            
        });
       
    }

    public void loadLog(){
        panelLogPenerimaan.loadPenerimaan();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedMenuSetting = new silaris_client.tabbed.TabbedPaneCustom();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedMenuSetting, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedMenuSetting, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private silaris_client.tabbed.TabbedPaneCustom tabbedMenuSetting;
    // End of variables declaration//GEN-END:variables
}
