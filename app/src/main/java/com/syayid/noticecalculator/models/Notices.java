package com.syayid.noticecalculator.models;

public class Notices {
    private String tipe;
    private double harga, progresif;

    public  Notices() {

    }

    public Notices(String tipe, double harga, double progresif) {
        this.tipe = tipe;
        this.harga = harga;
        this.progresif = progresif;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public double getProgresif() {
        return progresif;
    }

    public void setProgresif(double progresif) {
        this.progresif = progresif;
    }

    @Override
    public String toString() {
        return "Notices{" +
                "tipe='" + tipe + '\'' +
                ", harga=" + harga +
                ", progresif=" + progresif +
                '}';
    }
}
