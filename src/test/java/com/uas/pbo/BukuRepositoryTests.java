package com.uas.pbo;

import com.uas.pbo.model.Buku;
import com.uas.pbo.repository.BukuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class BukuRepositoryTests {
    @Autowired
    private BukuRepository repo;

    @Test
    public void testAddNew() {
        Buku Buku = new Buku();
        Buku.setIsbn("97812345890");
        Buku.setJudul("Example Buku");
        Buku.setPenulis("John Doe");
        Buku.setTentangMetsur(true);
        Buku.setTentangKalkulus(false);
        Buku.setTentangAlin(true);

        Buku savedBuku = repo.save(Buku);

        Assertions.assertNotNull(savedBuku.getId(), "Buku ID should not be null after saving");
        // Additional assertions can be added to validate other properties of the saved Buku
    }

    @Test
    public void testUpdate() {
        Integer BukuId = 2;
        Optional<Buku> optionalBuku = repo.findById(BukuId);
        Buku Buku = optionalBuku.get();
        Buku.setPenulis("Jane Doe");
        repo.save(Buku);

        Buku updatedBuku = repo.findById(BukuId).get();
        Assertions.assertEquals("Jane Doe", updatedBuku.getPenulis(), "Penulis should match");
    }

    @Test
    public void testGet() {
        List<Buku> bukus = (List<Buku>) repo.findAll();
        System.out.println("List of Buku:");
        for (Buku buku : bukus) {
            System.out.println(buku);
        }
    }

    @Test
    public void testDelete() {
        Integer BukuId = 1;
        repo.deleteById(BukuId);

        Optional<Buku> optionalBuku = repo.findById(BukuId);
        Assertions.assertFalse(optionalBuku.isPresent(), "Buku should be deleted");
    }
}
