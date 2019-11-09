package com.example.e_vicemote.Model;

public class Data {
    private String nama_toko;
    private String info_barang;
    private String id_order;
    private String status_order;

    public Data(){

    }

    public Data(String nama_toko, String info_barang, String id_order, String status_order){
        this.nama_toko = nama_toko;
        this.info_barang = info_barang;
        this.id_order = id_order;
        this.status_order = status_order;
    }

    public String getNama_toko() {
        return nama_toko;
    }

    public String getInfo_barang() {
        return info_barang;
    }

    public String getId_order() {
        return id_order;
    }

    public String getStatus_order() {
        return status_order;
    }
}
