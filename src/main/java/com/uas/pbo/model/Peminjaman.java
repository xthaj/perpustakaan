package com.uas.pbo.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Peminjaman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Buku buku;

    @ManyToOne
    @JoinColumn(name = "eksemplar_id")
    private EksemplarBuku eksemplarBuku;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "tanggal_peminjaman")
    private Date tanggalPeminjaman;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public EksemplarBuku getEksemplarBuku() {
        return eksemplarBuku;
    }

    public void setEksemplarBuku(EksemplarBuku eksemplarBuku) {
        this.eksemplarBuku = eksemplarBuku;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public void setTanggalPeminjaman(Date tanggalPeminjaman) {
        this.tanggalPeminjaman = tanggalPeminjaman;
    }
}
