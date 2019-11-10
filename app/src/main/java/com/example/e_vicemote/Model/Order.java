package com.example.e_vicemote.Model;

public class Order {
    private String nama_pelanggan;
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private String id_order;
    private String status_order;

    public Order(){

    }

    public Order(String nama_pelanggan, int hour, int minute, int day, int month, int year, String id_order, String status_order) {
        this.nama_pelanggan = nama_pelanggan;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.id_order = id_order;
        this.status_order = status_order;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getId_order() {
        return id_order;
    }
    public String getStatus_order() {
        return status_order;
    }
}
