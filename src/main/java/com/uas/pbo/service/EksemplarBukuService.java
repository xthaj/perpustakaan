package com.uas.pbo.service;

import com.uas.pbo.model.Buku;
import com.uas.pbo.model.EksemplarBuku;
import com.uas.pbo.repository.BukuRepository;
import com.uas.pbo.repository.EksemplarBukuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public class EksemplarBukuService {
    @Autowired
    private EksemplarBukuRepository repo;
    @Autowired
    private BukuRepository bukuRepository;


    public int countEksemplar(String isbn) {
        return repo.countByBukuIsbn(isbn);
    }

    public List<EksemplarBuku> findByBukuIsbn(String isbn) {
        return repo.findByBukuIsbn(isbn);
    }

    public EksemplarBuku createEksemplarBukuByIsbn(String isbn) {
        Buku buku = bukuRepository.findByIsbn(isbn);
        if (buku != null) {
            EksemplarBuku eksemplarBuku = new EksemplarBuku();
            eksemplarBuku.setBuku(buku);
            eksemplarBuku.setSedangDipinjam(false);
            return repo.save(eksemplarBuku);
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
