package com.example.appscoba.model;

public class Data {
    private  String id,name,email;


    public  Data(){

    }
    public Data(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }
    @Override
    public String toString() {
        return name + "\n\n" + email; // Menggunakan atribut 'name' dan 'email' sebagai teks yang akan ditampilkan di ListView
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}