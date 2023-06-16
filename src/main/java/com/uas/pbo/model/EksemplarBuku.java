package com.uas.pbo.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
public class EksemplarBuku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    private Buku buku;

    @Column
    private Boolean sedangDipinjam;

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

    public Boolean getSedangDipinjam() {
        return sedangDipinjam;
    }

    public void setSedangDipinjam(Boolean sedangDipinjam) {
        this.sedangDipinjam = sedangDipinjam;
    }
}
