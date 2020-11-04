package com.example.myapplication.models;

import java.io.Serializable;

public class Products implements Serializable {
    private String ma;
    private String name;
    private int gia;
    private int soLuong;
    private int hinh;

    public Products(String ma, String name, int gia, int soLuong, int hinh) {
        this.ma = ma;
        this.name = name;
        this.gia = gia;
        this.soLuong = soLuong;
        this.hinh = hinh;
    }

    public Products() {
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "Products{" +
                "ma='" + ma + '\'' +
                ", name='" + name + '\'' +
                ", gia=" + gia +
                ", hinh=" + hinh +
                '}';
    }
}
