package com.uas.pbo.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String nim;
    @Column(length = 20, nullable = false)
    private String password;
    @Column(length = 100, nullable = false)
    private String nama;
    @Column(nullable = false)
    private Boolean adalah_pustakawan;


    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Boolean getAdalah_pustakawan() {
        return adalah_pustakawan;
    }

    public void setAdalah_pustakawan(Boolean adalah_pustakawan) {
        this.adalah_pustakawan = adalah_pustakawan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
