/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package silaris_client.menu;

import java.awt.Font;
import silaris_client.Main;
import silaris_client.panel.PanelDataLinen;
import silaris_client.panel.PanelRuangan;

/**
 *
 * @author HP
 */
public class MenuMasterData extends javax.swing.JPanel {

    private Main main;
    private PanelDataLinen panelDataLinen;
    private PanelRuangan panelRuangan;
    public MenuMasterData(Main main) {
        this.main = main;
        initComponents();
        
        panelDataLinen = new PanelDataLinen(main);
        panelRuangan = new PanelRuangan(main);
        
        // set font tab menjadi Times New Roman
        tabbedMenuMasterData.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        tabbedMenuMasterData.addTab("Data Linen", panelDataLinen);
        tabbedMenuMasterData.addTab("Data Ruangan", panelRuangan);
        
        tabbedMenuMasterData.addChangeListener(e -> {
            int i = tabbedMenuMasterData.getSelectedIndex();
            String title = tabbedMenuMasterData.getTitleAt(i);

            if (title.equals("Data Linen")) {
                loadLinen();
            }
            if (title.equals("Data Ruangan")) {
                loadRuangan();
            }
        });
        
    }

    public void loadLinen(){
        panelDataLinen.loadLinen();
    }
    public void loadRuangan(){
        panelRuangan.loadData();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedMenuMasterData = new silaris_client.tabbed.TabbedPaneCustom();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedMenuMasterData, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedMenuMasterData, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private silaris_client.tabbed.TabbedPaneCustom tabbedMenuMasterData;
    // End of variables declaration//GEN-END:variables
}
