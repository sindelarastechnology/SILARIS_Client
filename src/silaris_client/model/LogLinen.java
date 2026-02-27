/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.model;

/**
 *
 * @author HP
 */
public class LogLinen {
    private String idLog;
    private String tanggal;
    private String petugas;
    private String ruangan;
    private Integer totalLinen;
    private String kategori;

    public LogLinen() {}

    public LogLinen(String tanggal, String petugas, String ruangan, String kategori, Integer totalLinen) {
        this.tanggal = tanggal;
        this.petugas = petugas;
        this.ruangan = ruangan;
        this.totalLinen = totalLinen;
        this.kategori = kategori;
    }

    public String getIdLog() { return idLog; }
    public void setIdLog(String idLog) { this.idLog = idLog; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getPetugas() { return petugas; }
    public void setPetugas(String petugas) { this.petugas = petugas; }

    public String getRuangan() { return ruangan; }
    public void setRuangan(String ruangan) { this.ruangan = ruangan; }
    
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public Integer getTotalLinen() {
        return totalLinen;
    }

    public void setTotalLinen(Integer totalLinen) {
        this.totalLinen = totalLinen;
    }
    
    
    
}
