package com.syayid.noticecalculator.models;

public class ArsipDetail {
    private int id, id_arsip;
    private String tipe;
    private int jumlah;
    private double harga, total;

    public ArsipDetail() {
    }

    public ArsipDetail(int id, int id_arsip, String tipe, int jumlah, double harga, double total) {
        this.id = id;
        this.id_arsip = id_arsip;
        this.tipe = tipe;
        this.jumlah = jumlah;
        this.harga = harga;
        this.total = total;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ArsipDetail{" +
                "id=" + id +
                ", id_arsip=" + id_arsip +
                ", tipe='" + tipe + '\'' +
                ", jumlah=" + jumlah +
                ", harga=" + harga +
                ", total=" + total +
                '}';
    }
}
