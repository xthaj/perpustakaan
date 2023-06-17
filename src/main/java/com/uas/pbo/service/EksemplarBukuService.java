package com.uas.pbo.service;

import com.uas.pbo.exceptions.BukuNotFoundException;
import com.uas.pbo.model.Buku;
import com.uas.pbo.model.EksemplarBuku;
import com.uas.pbo.model.User;
import com.uas.pbo.model.Waitlist;
import com.uas.pbo.repository.BukuRepository;
import com.uas.pbo.repository.EksemplarBukuRepository;
import com.uas.pbo.repository.WaitlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EksemplarBukuService {
    @Autowired
    private EksemplarBukuRepository repo;
    @Autowired
    private BukuService bukuService;
    @Autowired
    private BukuRepository bukuRepository;
    @Autowired
    private WaitlistService waitlistService;



    public int countEksemplar(String isbn) {
        return repo.countByBukuIsbn(isbn);
    }

    public void save(EksemplarBuku eksemplarBuku) { repo.save(eksemplarBuku); }

    public List<EksemplarBuku> findByBukuIsbn(String isbn) {
        return repo.findByBukuIsbn(isbn);
    }

    public List<EksemplarBuku> findByBukuId(Integer id) throws BukuNotFoundException {
        Optional<EksemplarBuku> result = repo.findById(id);
        if (result.isPresent()) {
            List<EksemplarBuku> eksemplarBukuList = new ArrayList<>();
            eksemplarBukuList.add(result.get());
            return eksemplarBukuList;
        }

        throw new BukuNotFoundException();
    }


    public EksemplarBuku createEksemplarBukuByIsbn(String isbn) {
        Buku buku = bukuRepository.findByIsbn(isbn);
        if (buku != null) {
            EksemplarBuku eksemplarBuku = new EksemplarBuku();
            eksemplarBuku.setBuku(buku);
            eksemplarBuku.setSedangDipinjam(false);
            EksemplarBuku createdEksemplarBuku = repo.save(eksemplarBuku);

            // Update waitlist
            List<Waitlist> waitlistEntries = waitlistService.getWaitlistByBukuId(buku.getId());

            for (Waitlist waitlist : waitlistEntries) {
                User user = waitlist.getUser();
                waitlist.setUpdated(true);
                waitlistService.save(waitlist);
            }

            return createdEksemplarBuku;
        }
        return null;
    }

    public void deleteEksemplarBukuByIsbn(String isbn, RedirectAttributes redirectAttributes) {
        List<EksemplarBuku> eksemplars = findByBukuIsbn(isbn);

        boolean anyEksemplarDeleted = false;

        for (EksemplarBuku eksemplar : eksemplars) {
            if (!eksemplar.getSedangDipinjam()) {
                repo.delete(eksemplar);
                anyEksemplarDeleted = true;
                break;
            }
        }

        if (anyEksemplarDeleted) {
            redirectAttributes.addFlashAttribute("message", "Berhasil menghapus eksemplar yang sedang tidak dipinjam.");
        } else {
            redirectAttributes.addFlashAttribute("message", "Tidak ada eksemplar yang sedang tidak dipinjam.");
        }
    }




}