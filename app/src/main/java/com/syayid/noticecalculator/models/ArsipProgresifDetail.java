package com.syayid.noticecalculator.models;

public class ArsipProgresifDetail {
    private int id, id_arsip_progresif;
    private String tipe;
    private int progresif_ke;
    private double biaya, subtotal;

    public ArsipProgresifDetail() {
    }

    public ArsipProgresifDetail(int id, int id_arsip_progresif, String tipe, int progresif_ke, double biaya, double subtotal) {
        this.id = id;
        this.id_arsip_progresif = id_arsip_progresif;
        this.tipe = tipe;
        this.progresif_ke = progresif_ke;
        this.biaya = biaya;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_arsip_progresif() {
        return id_arsip_progresif;
    }

    public void setId_arsip_progresif(int id_arsip_progresif) {
        this.id_arsip_progresif = id_arsip_progresif;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public int getProgresif_ke() {
        return progresif_ke;
    }

    public void setProgresif_ke(int progresif_ke) {
        this.progresif_ke = progresif_ke;
    }

    public double getBiaya() {
        return biaya;
    }

    public void setBiaya(double biaya) {
        this.biaya = biaya;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "ArsipProgresifDetail{" +
                "id=" + id +
                ", id_arsip_progresif=" + id_arsip_progresif +
                ", tipe='" + tipe + '\'' +
                ", progresif_ke=" + progresif_ke +
                ", biaya=" + biaya +
                ", subtotal=" + subtotal +
                '}';
    }
}
