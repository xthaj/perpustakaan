package com.uas.pbo.service;

import com.uas.pbo.exceptions.PeminjamanNotFoundException;
import com.uas.pbo.model.*;
import com.uas.pbo.repository.EksemplarBukuRepository;
import com.uas.pbo.repository.PeminjamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeminjamanService {
    @Autowired
    private PeminjamanRepository peminjamanRepository;
    @Autowired
    private EksemplarBukuRepository eksemplarRepository;
    @Autowired
    private WaitlistService waitlistService;

    public Peminjaman save(Peminjaman peminjaman) {
        return peminjamanRepository.save(peminjaman);
    }

    public Peminjaman getById(Integer id) throws PeminjamanNotFoundException {
        Optional<Peminjaman> peminjamanOptional = peminjamanRepository.findById(id);
        if (peminjamanOptional.isPresent()) {
            return peminjamanOptional.get();
        }
        throw new PeminjamanNotFoundException();
    }

    public List<Peminjaman> listAll() {
        return (List<Peminjaman>) peminjamanRepository.findAll();
    }

    public void delete(Integer id) throws PeminjamanNotFoundException {
        if (peminjamanRepository.existsById(id)) {
            peminjamanRepository.deleteById(id);
        } else {
            throw new PeminjamanNotFoundException();
        }
    }

    public void confirmPeminjaman(Integer id) throws PeminjamanNotFoundException {
        Peminjaman peminjaman = getById(id);

        // Perform the necessary operations to confirm the peminjaman
        EksemplarBuku eksemplarBuku = peminjaman.getEksemplarBuku();
        eksemplarBuku.setSedangDipinjam(false); // Set the eksemplar buku status to 0 (sedangDipinjam)

        // Save the updated peminjaman and eksemplar buku
        peminjamanRepository.delete(peminjaman);
        eksemplarRepository.save(eksemplarBuku);

        // Trigger notification and update waitlist
        Buku buku = eksemplarBuku.getBuku();
        List<Waitlist> waitlistEntries = waitlistService.getWaitlistByBukuId(buku.getId());

        for (Waitlist waitlist : waitlistEntries) {
            User user = waitlist.getUser();
            waitlist.setUpdated(true);
            waitlistService.save(waitlist);
        }
    }

    public List<Peminjaman> findByUserId(Integer userId) {
        return peminjamanRepository.findByUserId(userId);
    }

    public int getTotalPeminjamanCount() {
        return peminjamanRepository.getTotalCount();
    }



}
