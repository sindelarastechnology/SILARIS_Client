/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.model;

/**
 *
 * @author ASUS
 */
public class Ruangan {
    public String id;
    public String kodeRuangan;
    public String namaRuangan;
    public String keterangan;
    
    public String getId() {
        return id;
    }

    public String getKode() {
        return kodeRuangan;
    }

    public String getNama() {
        return namaRuangan;
    }

    @Override
    public String toString() {
        return namaRuangan; 
        // Ini yang tampil di ComboBox
    }
    
}
