package com.syayid.noticecalculator.models;

public class ArsipGabunganDetail {
    private int id, id_arsip;
    private String tipe;
    private int jumlah;
    private double harga;

    private int progresif_ke;
    private double progresif_biaya;

    private double subtotal;

    public ArsipGabunganDetail() {
    }

    public ArsipGabunganDetail(int id, int id_arsip, String tipe, int jumlah, double harga, int progresif_ke, double progresif_biaya, double subtotal) {
        this.id = id;
        this.id_arsip = id_arsip;
        this.tipe = tipe;
        this.jumlah = jumlah;
        this.harga = harga;
        this.progresif_ke = progresif_ke;
        this.progresif_biaya = progresif_biaya;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_arsip() {
        return id_arsip;
    }

    public void setId_arsip(int id_arsip) {
        this.id_arsip = id_arsip;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getProgresif_ke() {
        return progresif_ke;
    }

    public void setProgresif_ke(int progresif_ke) {
        this.progresif_ke = progresif_ke;
    }

    public double getProgresif_biaya() {
        return progresif_biaya;
    }

    public void setProgresif_biaya(double progresif_biaya) {
        this.progresif_biaya = progresif_biaya;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "ArsipGabunganDetail{" +
                "id=" + id +
                ", id_arsip=" + id_arsip +
                ", tipe='" + tipe + '\'' +
                ", jumlah=" + jumlah +
                ", harga=" + harga +
                ", progresif_ke=" + progresif_ke +
                ", progresif_biaya=" + progresif_biaya +
                ", subtotal=" + subtotal +
                '}';
    }
}
