package com.example.allysaas.heple.model;

import android.graphics.Bitmap;

public class PostInformasiModel {
    private String nama;
    private String alamat;
    private String lokasiBerjualan;
    private String petaLokasi;
    private String noHp;
    private String statusBerjualan;
    private String deskripsi;


    public PostInformasiModel(String nama, String alamat, String lokasiBerjualan, String petaLokasi, String noHp, String statusBerjualan, String deskripsi) {
        this.nama = nama;
        this.alamat = alamat;
        this.lokasiBerjualan = lokasiBerjualan;
        this.petaLokasi = petaLokasi;
        this.noHp = noHp;
        this.statusBerjualan = statusBerjualan;
        this.deskripsi = deskripsi;

    }
}
