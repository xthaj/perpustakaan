package com.uas.pbo;

import com.uas.pbo.model.Buku;
import com.uas.pbo.model.EksemplarBuku;
import com.uas.pbo.repository.BukuRepository;
import com.uas.pbo.repository.EksemplarBukuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class EksemplarBukuRepositoryTests {
    @Autowired
    private EksemplarBukuRepository eksemplarBukuRepository;

    @Autowired
    private BukuRepository bukuRepository;

    @Test
    public void testAddNew() {
        // Create a Buku
        Buku buku = new Buku();
        buku.setIsbn("123");
        buku.setJudul("Your Buku Judul");
        buku.setPenulis("Your Buku Penulis");
        buku.setTentangMetsur(true);
        buku.setTentangKalkulus(false);
        buku.setTentangAlin(true);

        Buku savedBuku = bukuRepository.save(buku);
        Assertions.assertNotNull(savedBuku.getId(), "Buku ID should not be null after saving");

        // Create an EksemplarBuku associated with the Buku
        EksemplarBuku eksemplarBuku = new EksemplarBuku();
        eksemplarBuku.setBuku(savedBuku);
        eksemplarBuku.setSedangDipinjam(false);

        EksemplarBuku savedEksemplarBuku = eksemplarBukuRepository.save(eksemplarBuku);
        Assertions.assertNotNull(savedEksemplarBuku.getId(), "EksemplarBuku ID should not be null after saving");

        // Retrieve the saved EksemplarBuku and validate the associations
        Optional<EksemplarBuku> retrievedEksemplarBuku = eksemplarBukuRepository.findById(savedEksemplarBuku.getId());
        Assertions.assertTrue(retrievedEksemplarBuku.isPresent(), "EksemplarBuku should be present");
        assertEquals(savedBuku.getId(), retrievedEksemplarBuku.get().getBuku().getId(), "Buku ID should match");

    }

    @Test
    public void testCountByBukuIsbn() {

        Buku Buku1 = createDummyBuku("222");

        Buku savedBuku = bukuRepository.save(Buku1);
        Assertions.assertNotNull(savedBuku.getId(), "Buku ID should not be null after saving");

        // Create an EksemplarBuku associated with the Buku
        EksemplarBuku eksemplarBuku1 = new EksemplarBuku();
        eksemplarBuku1.setBuku(savedBuku);
        eksemplarBuku1.setSedangDipinjam(false);

        EksemplarBuku eksemplarBuku2 = new EksemplarBuku();
        eksemplarBuku2.setBuku(savedBuku);
        eksemplarBuku2.setSedangDipinjam(false);

        EksemplarBuku eksemplarBuku3 = new EksemplarBuku();
        eksemplarBuku3.setBuku(savedBuku);
        eksemplarBuku3.setSedangDipinjam(false);

        // Count the number of EksemplarBuku associated with the Buku by ISBN
        int count = eksemplarBukuRepository.countByBukuIsbn("222");
        System.out.println("Count of EksemplarBuku for Buku with ISBN " + "222" + ": " + count);
        assertEquals(3, count, "Count should match the expected value");

    }

    // Helper method to create a dummy Buku
    private Buku createDummyBuku(String isbn) {
        Buku buku = new Buku();
        buku.setIsbn(isbn);
        buku.setJudul("Dummy Buku Judul");
        buku.setPenulis("Dummy Buku Penulis");
        buku.setTentangMetsur(false);
        buku.setTentangKalkulus(true);
        buku.setTentangAlin(false);
        return bukuRepository.save(buku);
    }

    @Test
    public void testFindByBukuIsbn() {
        // Create a test book
        Buku book = new Buku();
        book.setIsbn("1234567890"); // Replace with an existing ISBN in your database

        // Save the book to the database
        Buku savedBook = bukuRepository.save(book);

        // Create some test copies
        EksemplarBuku copy1 = new EksemplarBuku();
        copy1.setBuku(savedBook);
        copy1.setSedangDipinjam(true); // Set the desired values for testing
        eksemplarBukuRepository.save(copy1);

        EksemplarBuku copy2 = new EksemplarBuku();
        copy2.setBuku(savedBook);
        copy2.setSedangDipinjam(false); // Set the desired values for testing
        eksemplarBukuRepository.save(copy2);

        // Perform the query
        List<EksemplarBuku> copies = eksemplarBukuRepository.findByBukuIsbn(savedBook.getIsbn());

        // Assert the results
        assertEquals(2, copies.size());
        // Add more assertions if needed
    }
}
