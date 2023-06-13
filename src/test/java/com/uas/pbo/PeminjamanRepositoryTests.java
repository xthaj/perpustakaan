package com.uas.pbo;

import com.uas.pbo.model.Buku;
import com.uas.pbo.model.EksemplarBuku;
import com.uas.pbo.model.Peminjaman;
import com.uas.pbo.model.User;
import com.uas.pbo.repository.PeminjamanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import java.util.Date;
import java.util.List;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class PeminjamanRepositoryTests {
    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Test
    public void testCRUD() {
        // Create
        Buku buku = new Buku();
        buku.setIsbn("9781234567890");
        buku.setJudul("Example Book");
        buku.setPenulis("John Doe");
        buku.setTentangMetsur(true);
        buku.setTentangKalkulus(false);
        buku.setTentangAlin(true);

//        EksemplarBuku eksemplarBuku = new EksemplarBuku();
//        eksemplarBuku.setBuku(buku);
//        eksemplarBuku.setSedangDipinjam(false);
//
//        User user = new User();
//        user.setNim("123456789");
//        user.setPassword("password");
//        user.setNama("John Doe");
//        user.setAdalah_pustakawan(false);
//
//        Peminjaman peminjaman = new Peminjaman();
//        peminjaman.setBuku(buku);
//        peminjaman.setEksemplarBuku(eksemplarBuku);
//        peminjaman.setUser(user);
//        peminjaman.setTanggalPeminjaman(new Date());
//
//        Peminjaman savedPeminjaman = peminjamanRepository.save(peminjaman);
//        Assertions.assertNotNull(savedPeminjaman.getId());

        // Read
//        Peminjaman retrievedPeminjaman = peminjamanRepository.findById(savedPeminjaman.getId()).orElse(null);
//        Assertions.assertNotNull(retrievedPeminjaman);
//        Assertions.assertEquals(savedPeminjaman.getId(), retrievedPeminjaman.getId());

//        // Update
//        retrievedPeminjaman.setTanggalPeminjaman(new Date());
//        Peminjaman updatedPeminjaman = peminjamanRepository.save(retrievedPeminjaman);
//        Assertions.assertEquals(retrievedPeminjaman.getTanggalPeminjaman(), updatedPeminjaman.getTanggalPeminjaman());

        // Delete
//        peminjamanRepository.deleteById(updatedPeminjaman.getId());
//        Peminjaman deletedPeminjaman = peminjamanRepository.findById(updatedPeminjaman.getId()).orElse(null);
//        Assertions.assertNull(deletedPeminjaman);
    }
}
