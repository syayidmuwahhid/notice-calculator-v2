package com.syayid.noticecalculator.models;

public class ProsesPrices {
    private int id;
    private String wilayah;
    private double harga;

    public ProsesPrices() {
    }

    public ProsesPrices(int id, String wilayah, double harga) {
        this.id = id;
        this.wilayah = wilayah;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return "ProsesPrices{" +
                "id=" + id +
                ", wilayah='" + wilayah + '\'' +
                ", harga=" + harga +
                '}';
    }
}
