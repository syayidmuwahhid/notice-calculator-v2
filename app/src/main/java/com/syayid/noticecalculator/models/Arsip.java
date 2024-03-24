package com.syayid.noticecalculator.models;

public class Arsip {
    private int id, jumlah_faktur;
    private String tanggal, wilayah;
    private double biaya_proses;
    private long total_seluruh;

    public Arsip() {
    }

    public Arsip(int id, String tanggal, String wilayah, double biaya_proses, int jumlah_faktur, long total_seluruh) {
        this.id = id;
        this.jumlah_faktur = jumlah_faktur;
        this.tanggal = tanggal;
        this.wilayah = wilayah;
        this.biaya_proses = biaya_proses;
        this.total_seluruh = total_seluruh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJumlah_faktur() {
        return jumlah_faktur;
    }

    public void setJumlah_faktur(int jumlah_faktur) {
        this.jumlah_faktur = jumlah_faktur;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public double getBiaya_proses() {
        return biaya_proses;
    }

    public void setBiaya_proses(double biaya_proses) {
        this.biaya_proses = biaya_proses;
    }

    public long getTotal_seluruh() {
        return total_seluruh;
    }

    public void setTotal_seluruh(long total_seluruh) {
        this.total_seluruh = total_seluruh;
    }

    @Override
    public String toString() {
        return "Arsip{" +
                "id=" + id +
                ", jumlah_faktur=" + jumlah_faktur +
                ", tanggal='" + tanggal + '\'' +
                ", wilayah='" + wilayah + '\'' +
                ", biaya_proses=" + biaya_proses +
                ", total_seluruh=" + total_seluruh +
                '}';
    }

}
