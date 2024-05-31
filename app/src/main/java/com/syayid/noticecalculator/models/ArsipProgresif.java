package com.syayid.noticecalculator.models;

public class ArsipProgresif {
    private int id;
    private String tanggal;
    private long total;

    public ArsipProgresif() {
    }

    public ArsipProgresif(int id, String tanggal, long total) {
        this.id = id;
        this.tanggal = tanggal;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ArsipProgresif{" +
                "id=" + id +
                ", tanggal='" + tanggal + '\'' +
                ", total=" + total +
                '}';
    }
}
