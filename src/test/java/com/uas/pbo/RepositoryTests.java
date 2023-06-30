package com.uas.pbo;

import com.uas.pbo.model.*;
import com.uas.pbo.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
public class RepositoryTests {
    @Autowired
    private BukuRepository bukuRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EksemplarBukuRepository eksemplarBukuRepository;
    @Autowired
    private PeminjamanRepository peminjamanRepository;
    @Autowired
    private WaitlistRepository waitlistRepository;

    /*
     * ========================================
     * |                                      |
     * |              USER                    |
     * |                                      |
     * ========================================
     */

    @Test
    public void testAddNew() {
        User user = new User();
        user.setNim("1");
        user.setPassword("1");
        user.setNama("Alex Apollonov");
        user.setAdalah_pustakawan(true);

        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser.getId(), "User ID should not be null after saving");
    }

    @Test
    public void testCountById() {
        User user = new User();
        user.setNim("2");
        user.setPassword("2");
        user.setNama("John Doe");
        user.setAdalah_pustakawan(false);
        userRepository.save(user);
        long count = userRepository.countById(user.getId());
        Assertions.assertEquals(1, count, "User count should be 1");
    }

    @Test
    public void testGetNamaById() {
        // Save a user to the database
        User user = new User();
        user.setNim("3");
        user.setPassword("3");
        user.setNama("Jane Smith");
        user.setAdalah_pustakawan(false);
        userRepository.save(user);

        // Perform getNamaById operation
        String nama = userRepository.getNamaById(user.getId());

        Assertions.assertEquals("Jane Smith", nama, "User name should be Jane Smith");
    }

    @Test
    public void testFindByNim() {
        // Save a user to the database
        User user = new User();
        user.setNim("4");
        user.setPassword("4");
        user.setNama("Bob Johnson");
        user.setAdalah_pustakawan(false);
        userRepository.save(user);

        // Perform findByNim operation
        User foundUser = userRepository.findByNim("4");

        Assertions.assertNotNull(foundUser, "User should not be null");
        Assertions.assertEquals("Bob Johnson", foundUser.getNama(), "User name should be Bob Johnson");
    }

    /*
     * ========================================
     * |                                      |
     * |              BUKU                    |
     * |                                      |
     * ========================================
     */

    @Test
    public void testSave() {
        Buku buku = new Buku();
        buku.setIsbn("123456789");
        buku.setJudul("Example Book");
        buku.setPenulis("John Doe");
        buku.setTentangMetsur(true);
        buku.setTentangKalkulus(false);
        buku.setTentangAlin(true);

        Buku savedBuku =bukuRepository.save(buku);

        assertNotNull(savedBuku.getId(), "Buku ID should not be null after saving");
    }

    @Test
    public void testGet() {
        Buku buku = new Buku();
        buku.setIsbn("123456789");
        buku.setJudul("Example Book");
        buku.setPenulis("John Doe");
        buku.setTentangMetsur(true);
        buku.setTentangKalkulus(false);
        buku.setTentangAlin(true);
        bukuRepository.save(buku);

        Optional<Buku> retrievedBuku = bukuRepository.findById(buku.getId());

        Assertions.assertTrue(retrievedBuku.isPresent(), "Buku should be retrieved");
        assertEquals("Example Book", retrievedBuku.get().getJudul(), "Buku judul should match");
    }

    @Test
    public void testSaveEdit() {
        Buku buku = new Buku();
        buku.setIsbn("123456789");
        buku.setJudul("Example Book");
        buku.setPenulis("John Doe");
        buku.setTentangMetsur(true);
        buku.setTentangKalkulus(false);
        buku.setTentangAlin(true);
        bukuRepository.save(buku);

        buku.setJudul("Updated Book");
        Buku updatedBuku =bukuRepository.save(buku);

        assertEquals("Updated Book", updatedBuku.getJudul(), "Buku judul should be updated");
    }

    @Test
    public void testDelete() {
        Buku buku = new Buku();
        buku.setIsbn("123456789");
        buku.setJudul("Example Book");
        buku.setPenulis("John Doe");
        buku.setTentangMetsur(true);
        buku.setTentangKalkulus(false);
        buku.setTentangAlin(true);
       bukuRepository.save(buku);

       bukuRepository.delete(buku);

        assertFalse(bukuRepository.existsById(buku.getId()), "Buku should be deleted");
    }

    @Test
    public void testFindByIsbn() {
        Buku buku = new Buku();
        buku.setIsbn("123456789");
        buku.setJudul("Example Book");
        buku.setPenulis("John Doe");
        buku.setTentangMetsur(true);
        buku.setTentangKalkulus(false);
        buku.setTentangAlin(true);
        bukuRepository.save(buku);

        Buku foundBuku =bukuRepository.findByIsbn("123456789");

        assertNotNull(foundBuku, "Buku should not be null");
        assertEquals("Example Book", foundBuku.getJudul(), "Buku judul should match");
    }

    @Test
    public void testDeleteById() {
        Buku buku = new Buku();
        buku.setIsbn("123456789");
        buku.setJudul("Example Book");
        buku.setPenulis("John Doe");
        buku.setTentangMetsur(true);
        buku.setTentangKalkulus(false);
        buku.setTentangAlin(true);
       bukuRepository.save(buku);

       bukuRepository.deleteById(buku.getId());

        assertFalse(bukuRepository.existsById(buku.getId()), "Buku should be deleted");
    }


    /*
     * ========================================
     * |                                      |
     * |              EKSEMPLAR               |
     * |                                      |
     * ========================================
     */

    @Test
    public void testSaveEks() {
        Buku buku = new Buku();
        buku.setIsbn("1234567890");
        buku.setJudul("Example Book");
        bukuRepository.save(buku);

        EksemplarBuku eksemplar = new EksemplarBuku();
        eksemplar.setBuku(buku);
        eksemplar.setSedangDipinjam(false);
        eksemplarBukuRepository.save(eksemplar);

        assertNotNull(eksemplar.getId());
    }

    @Test
    public void testCountByBukuIsbn() {
        Buku buku = new Buku();
        buku.setIsbn("1234567890");
        buku.setJudul("Example Book");
        bukuRepository.save(buku);

        EksemplarBuku eksemplar1 = new EksemplarBuku();
        eksemplar1.setBuku(buku);
        eksemplar1.setSedangDipinjam(false);
        eksemplarBukuRepository.save(eksemplar1);

        EksemplarBuku eksemplar2 = new EksemplarBuku();
        eksemplar2.setBuku(buku);
        eksemplar2.setSedangDipinjam(true);
        eksemplarBukuRepository.save(eksemplar2);

        int count = eksemplarBukuRepository.countByBukuIsbn("1234567890");
        assertEquals(2, count);
    }

    @Test
    public void testFindByBukuIsbn() {
        Buku buku1 = new Buku();
        buku1.setIsbn("1234567890");
        buku1.setJudul("Example Book 1");
        bukuRepository.save(buku1);

        Buku buku2 = new Buku();
        buku2.setIsbn("0987654321");
        buku2.setJudul("Example Book 2");
        bukuRepository.save(buku2);

        EksemplarBuku eksemplar1 = new EksemplarBuku();
        eksemplar1.setBuku(buku1);
        eksemplar1.setSedangDipinjam(false);
        eksemplarBukuRepository.save(eksemplar1);

        EksemplarBuku eksemplar2 = new EksemplarBuku();
        eksemplar2.setBuku(buku1);
        eksemplar2.setSedangDipinjam(true);
        eksemplarBukuRepository.save(eksemplar2);

        EksemplarBuku eksemplar3 = new EksemplarBuku();
        eksemplar3.setBuku(buku2);
        eksemplar3.setSedangDipinjam(false);
        eksemplarBukuRepository.save(eksemplar3);

        List<EksemplarBuku> foundEksemplars = eksemplarBukuRepository.findByBukuIsbn("1234567890");
        assertEquals(2, foundEksemplars.size());
    }

    @Test
    public void testDeleteEks() {
        Buku buku = new Buku();
        buku.setIsbn("1234567890");
        buku.setJudul("Example Book");
        bukuRepository.save(buku);

        EksemplarBuku eksemplar = new EksemplarBuku();
        eksemplar.setBuku(buku);
        eksemplar.setSedangDipinjam(false);
        eksemplarBukuRepository.save(eksemplar);

        eksemplarBukuRepository.delete(eksemplar);

        Assertions.assertNull(eksemplarBukuRepository.findById(eksemplar.getId()).orElse(null));
    }

    /*
     * ========================================
     * |                                      |
     * |              PEMINJAMAN              |
     * |                                      |
     * ========================================
     */

    @Test
    public void testCreateAndDeletePeminjaman() {
        // Create a new Peminjaman
        Peminjaman peminjaman = new Peminjaman();
        // Set the required properties
        Buku buku = new Buku();
        buku.setIsbn("ISBN123");
        // Set other properties for the Buku entity if necessary

        EksemplarBuku eksemplarBuku = new EksemplarBuku();
        // Set properties for the EksemplarBuku entity if necessary

        User user = new User();
        // Set properties for the User entity if necessary

        peminjaman.setBuku(buku);
        peminjaman.setEksemplarBuku(eksemplarBuku);
        peminjaman.setUser(user);
        peminjaman.setTanggalPeminjaman(new Date());

        // Save the Peminjaman
        Peminjaman savedPeminjaman = peminjamanRepository.save(peminjaman);
        Integer peminjamanId = savedPeminjaman.getId();

        // Verify that the Peminjaman was saved correctly
        assertNotNull(peminjamanId);

        // Delete the Peminjaman
        peminjamanRepository.delete(savedPeminjaman);

        // Verify that the Peminjaman was deleted
        assertFalse(peminjamanRepository.existsById(peminjamanId));
    }

    @Test
    public void testFindByUserIdPeminj() {
        // Create a test User
        User user = new User();
        user.setNim("2");
        user.setPassword("2");
        user.setNama("John Doe");
        user.setAdalah_pustakawan(false);
        // Set properties for the User entity if necessary

        // Save the User
        User savedUser = userRepository.save(user);

        // Create multiple Peminjaman instances associated with the test User
        Peminjaman peminjaman1 = new Peminjaman();
        peminjaman1.setUser(savedUser);
        // Set other properties for peminjaman1 if necessary

        Peminjaman peminjaman2 = new Peminjaman();
        peminjaman2.setUser(savedUser);
        // Set other properties for peminjaman2 if necessary

        // Save the Peminjaman instances
        peminjamanRepository.save(peminjaman1);
        peminjamanRepository.save(peminjaman2);

        // Find Peminjaman instances by UserId
        List<Peminjaman> peminjamans = peminjamanRepository.findByUserId(savedUser.getId());

        // Verify that the correct number of Peminjaman instances were returned
        assertEquals(2, peminjamans.size());
    }

    @Test
    public void testExistsByUserIdAndBukuIsbn() {
        // Create a test User
        User user = new User();
        user.setNim("2");
        user.setPassword("2");
        user.setNama("John Doe");
        user.setAdalah_pustakawan(false);
        // Set properties for the User entity if necessary

        // Save the User
        User savedUser = userRepository.save(user);

        // Create a test Buku
        Buku buku = new Buku();
        buku.setIsbn("ISBN123");
        // Set other properties for the Buku entity if necessary

        // Save the Buku
        Buku savedBuku = bukuRepository.save(buku);

        // Create a Peminjaman associated with the test User and Buku
        Peminjaman peminjaman = new Peminjaman();
        peminjaman.setUser(savedUser);
        peminjaman.setBuku(savedBuku);
        // Set other properties for the Peminjaman entity if necessary

        // Save the Peminjaman
        Peminjaman savedPeminjaman = peminjamanRepository.save(peminjaman);

        // Verify that the Peminjaman exists for the specified UserId and Buku ISBN
        assertTrue(peminjamanRepository.existsByUserIdAndBukuIsbn(savedUser.getId(), savedBuku.getIsbn()));
    }

    @Test
    public void testGetTotalCountPeminjaman() {
        // Create multiple Peminjaman instances
        Peminjaman peminjaman1 = new Peminjaman();
        // Set properties for peminjaman1 if necessary
        peminjamanRepository.save(peminjaman1);

        Peminjaman peminjaman2 = new Peminjaman();
        // Set properties for peminjaman2 if necessary
        peminjamanRepository.save(peminjaman2);

        // Get the total count of Peminjaman instances
        int totalCount = peminjamanRepository.getTotalCount();

        // Verify that the correct total count is returned
        assertEquals(2, totalCount);
    }


    /*
     * ========================================
     * |                                      |
     * |              WAITLIST                |
     * |                                      |
     * ========================================
     */

    @Test
    public void testExistsByUserAndBukuIdWaitlist() {
        // Create a test User
        User user = new User();
        user.setNim("2");
        user.setPassword("2");
        user.setNama("John Doe");
        user.setAdalah_pustakawan(false);

        // Save the User
        User savedUser = userRepository.save(user);

        // Create a test Buku
        Buku buku = new Buku();
        // Set properties for the Buku entity if necessary

        // Save the Buku
        Buku savedBuku = bukuRepository.save(buku);

        // Create a Waitlist associated with the test User and Buku
        Waitlist waitlist = new Waitlist();
        waitlist.setUser(savedUser);
        waitlist.setBuku(savedBuku);
        // Set other properties for the Waitlist entity if necessary

        // Save the Waitlist
        Waitlist savedWaitlist = waitlistRepository.save(waitlist);

        // Verify that the Waitlist exists for the specified User and BukuId
        assertTrue(waitlistRepository.existsByUserAndBukuId(savedUser, savedBuku.getId()));
    }

    @Test
    public void testFindByUserIdWaitlist() {
        // Create a test User
        User user = new User();
        user.setNim("2");
        user.setPassword("2");
        user.setNama("John Doe");
        user.setAdalah_pustakawan(false);

        // Save the User
        User savedUser = userRepository.save(user);

        // Create multiple Waitlist instances associated with the test User
        Waitlist waitlist1 = new Waitlist();
        waitlist1.setUser(savedUser);
        // Set other properties for waitlist1 if necessary

        Waitlist waitlist2 = new Waitlist();
        waitlist2.setUser(savedUser);
        // Set other properties for waitlist2 if necessary

        // Save the Waitlist instances
        waitlistRepository.save(waitlist1);
        waitlistRepository.save(waitlist2);

        // Find Waitlist instances by UserId
        List<Waitlist> waitlists = waitlistRepository.findByUserId(savedUser.getId());

        // Verify that the correct number of Waitlist instances were returned
        assertEquals(2, waitlists.size());
    }


    @Test
    public void testFindAll() {
        // Create multiple Waitlist instances
        Waitlist waitlist1 = new Waitlist();
        // Set properties for waitlist1 if necessary
        waitlistRepository.save(waitlist1);

        Waitlist waitlist2 = new Waitlist();
        // Set properties for waitlist2 if necessary
        waitlistRepository.save(waitlist2);

        // Find all Waitlist instances
        List<Waitlist> waitlists = waitlistRepository.findAll();

        // Verify that the correct number of Waitlist instances were returned
        assertEquals(2, waitlists.size());
    }

    @Test
    public void testFindByBukuId() {
        // Create a test Buku
        Buku buku = new Buku();
        // Set properties for the Buku entity if necessary

        // Save the Buku
        Buku savedBuku = bukuRepository.save(buku);

        // Create multiple Waitlist instances associated with the test Buku
        Waitlist waitlist1 = new Waitlist();
        waitlist1.setBuku(savedBuku);
        // Set other properties for waitlist1 if necessary

        Waitlist waitlist2 = new Waitlist();
        waitlist2.setBuku(savedBuku);
        // Set other properties for waitlist2 if necessary

        // Save the Waitlist instances
        waitlistRepository.save(waitlist1);
        waitlistRepository.save(waitlist2);

        // Find Waitlist instances by BukuId
        List<Waitlist> waitlists = waitlistRepository.findByBukuId(savedBuku.getId());

        // Verify that the correct number of Waitlist instances were returned
        assertEquals(2, waitlists.size());
    }

    @Test
    public void testFindByUserIdAndIsUpdated() {
        // Create a test User
        User user = new User();
        user.setNim("2");
        user.setPassword("2");
        user.setNama("John Doe");
        user.setAdalah_pustakawan(false);

        // Save the User
        User savedUser = userRepository.save(user);

        // Create multiple Waitlist instances associated with the test User
        Waitlist waitlist1 = new Waitlist();
        waitlist1.setUser(savedUser);
        waitlist1.setUpdated(true);
        // Set other properties for waitlist1 if necessary

        Waitlist waitlist2 = new Waitlist();
        waitlist2.setUser(savedUser);
        waitlist2.setUpdated(false);
        // Set other properties for waitlist2 if necessary

        // Save the Waitlist instances
        waitlistRepository.save(waitlist1);
        waitlistRepository.save(waitlist2);

        // Find Waitlist instances by UserId and IsUpdated
        List<Waitlist> waitlists = waitlistRepository.findByUserIdAndIsUpdated(savedUser.getId(), true);

        // Verify that the correct number of Waitlist instances were returned
        assertEquals(1, waitlists.size());
    }

    @Test
    public void testDeleteByIdWaitlist() {
        // Create a test Waitlist
        Waitlist waitlist = new Waitlist();
        // Set properties for the Waitlist entity if necessary

        // Save the Waitlist
        Waitlist savedWaitlist = waitlistRepository.save(waitlist);

        // Delete the Waitlist by Id
        waitlistRepository.deleteById(savedWaitlist.getId());

        // Verify that the Waitlist no longer exists
        assertFalse(waitlistRepository.existsById(savedWaitlist.getId()));
    }

    @Test
    public void testGetTotalCount() {
        // Create multiple Waitlist instances
        Waitlist waitlist1 = new Waitlist();
        // Set properties for waitlist1 if necessary
        waitlistRepository.save(waitlist1);

        Waitlist waitlist2 = new Waitlist();
        // Set properties for waitlist2 if necessary
        waitlistRepository.save(waitlist2);

        // Get the total count of Waitlist instances
        int totalCount = waitlistRepository.getTotalCount();

        // Verify that the correct total count is returned
        assertEquals(2, totalCount);
    }


}
