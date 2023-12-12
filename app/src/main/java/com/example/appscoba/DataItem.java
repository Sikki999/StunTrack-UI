package com.example.appscoba;
public class DataItem {

    private String agenda;
    private String jumlah;
    private String harga;

    public DataItem(String agenda, String jumlah, String harga) {
        this.agenda = agenda;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getAgenda() {
        return agenda;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getHarga() {
        return harga;
    }
}

