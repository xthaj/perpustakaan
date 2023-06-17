package com.uas.pbo.model;

import jakarta.persistence.*;

@Entity
public class Buku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String isbn;
    @Column
    private String judul;
    @Column
    private String penulis;
    @Column(name = "tentang_metsur")
    private Boolean tentangMetsur;
    @Column(name = "tentang_kalkulus")
    private Boolean tentangKalkulus;
    @Column(name = "tentang_alin")
    private Boolean tentangAlin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public Boolean getTentangMetsur() {
        return tentangMetsur;
    }

    public void setTentangMetsur(Boolean tentangMetsur) {
        this.tentangMetsur = tentangMetsur;
    }

    public Boolean getTentangKalkulus() {
        return tentangKalkulus;
    }

    public void setTentangKalkulus(Boolean tentangKalkulus) {
        this.tentangKalkulus = tentangKalkulus;
    }

    public Boolean getTentangAlin() {
        return tentangAlin;
    }

    public void setTentangAlin(Boolean tentangAlin) {
        this.tentangAlin = tentangAlin;
    }
}
