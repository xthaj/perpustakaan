package com.uas.pbo.controller;

import com.uas.pbo.exceptions.BukuNotFoundException;
import com.uas.pbo.exceptions.UserNotFoundException;
import com.uas.pbo.model.Buku;
import com.uas.pbo.model.EksemplarBuku;
import com.uas.pbo.model.Peminjaman;
import com.uas.pbo.model.User;
import com.uas.pbo.service.BukuService;
import com.uas.pbo.service.EksemplarBukuService;
import com.uas.pbo.service.PeminjamanService;
import com.uas.pbo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BukuService bukuService;
    @Autowired
    private EksemplarBukuService eksemplarBukuService;
    @Autowired
    private PeminjamanService peminjamanService;
    @GetMapping("/user/index")
    public String showDashboardUser(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        String userName = userService.getUserNameById(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);

        return "user/dashboard";
    }

    @GetMapping("/user/buku")
    public String exploreBooks(Model model) {
        List<Buku> listBuku = bukuService.listAll();
        model.addAttribute("listBuku", listBuku);
        model.addAttribute("eksemplarBukuService", eksemplarBukuService);

        return "user/buku";
    }

    @GetMapping("/user/peminjaman")
    public String viewPeminjaman(Model model) {
        // Add your logic here
        return "user/peminjaman";
    }

    @GetMapping("/buku/{id}/pinjam")
    public String createPeminjaman(@PathVariable("id") Integer bukuId, RedirectAttributes ra, HttpSession session) throws UserNotFoundException, BukuNotFoundException {
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.get(userId);

        // Find an available EksemplarBuku for the given bukuId
        List<EksemplarBuku> availableEksemplars = eksemplarBukuService.findByBukuId(bukuId);

        if (!availableEksemplars.isEmpty()) {
            EksemplarBuku availableEksemplar = null;

            for (EksemplarBuku eksemplar : availableEksemplars) {
                if (!eksemplar.getSedangDipinjam()) {
                    availableEksemplar = eksemplar;
                    break;
                }
            }

            if (availableEksemplar != null) {
                // Create a new Peminjaman object
                Peminjaman peminjaman = new Peminjaman();
                peminjaman.setBuku(availableEksemplar.getBuku());
                peminjaman.setEksemplarBuku(availableEksemplar);
                peminjaman.setUser(user);
                peminjaman.setTanggalPeminjaman(new Date());

                // Set the eksemplar to sedangDipinjam = true
                availableEksemplar.setSedangDipinjam(true);
                eksemplarBukuService.save(availableEksemplar);

                // Save the Peminjaman object
                peminjamanService.save(peminjaman);

                ra.addFlashAttribute("message", "Peminjaman berhasil. Buku telah dipinjam.");
            } else {
                ra.addFlashAttribute("message", "Tidak ada buku yang sedang tersedia. Silakan masuk ke dalam waitlist untuk dinotifikasikan bila buku telah tersedia. Lihat daftar peminjaman di tab peminjaman");
            }
        } else {
            ra.addFlashAttribute("message", "Tidak ada buku yang sedang tersedia. Silakan masuk ke dalam waitlist untuk dinotifikasikan bila buku telah tersedia.");
        }

        return "redirect:/user/buku";
    }



    @GetMapping("/user/waitlist")
    public String viewWaitlist(Model model) {
        // Add your logic here
        return "user/waitlist"; // Assuming "user/waitlist" is the view template
    }




}
