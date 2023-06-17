package com.uas.pbo.controller;

import com.uas.pbo.exceptions.BukuNotFoundException;
import com.uas.pbo.exceptions.UserNotFoundException;
import com.uas.pbo.model.*;
import com.uas.pbo.service.*;
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
    private WaitlistService waitlistService;
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

        // Get the waitlists that have been updated for the user
        List<Waitlist> updatedWaitlists = waitlistService.getUpdatedWaitlistsByUserId(userId);
        model.addAttribute("updatedWaitlists", updatedWaitlists);

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
    public String viewPeminjaman(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        List<Peminjaman> peminjamanList = peminjamanService.findByUserId(userId);
        model.addAttribute("listPeminjaman", peminjamanList);

        return "user/peminjaman";
    }


    @GetMapping("/buku/{isbn}/pinjam")
    public String createPeminjaman(@PathVariable("isbn") String isbn, RedirectAttributes ra, HttpSession session) throws UserNotFoundException, BukuNotFoundException {
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.get(userId);

        // Find an available EksemplarBuku for the given ISBN
        List<EksemplarBuku> availableEksemplars = eksemplarBukuService.findByBukuIsbn(isbn);

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

    @GetMapping("/buku/{id}/waitlist")
    public String joinWaitlist(@PathVariable("id") Integer bukuId, HttpSession session, RedirectAttributes ra) throws UserNotFoundException, BukuNotFoundException {
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.get(userId);

        // Check if the user is already on the waitlist for the book
        boolean isUserOnWaitlist = waitlistService.isUserOnWaitlist(user.getId(), bukuId);
        if (isUserOnWaitlist) {
            ra.addFlashAttribute("message", "You are already on the waitlist for this book.");
            return "redirect:/user/buku";
        }

        // Add the user to the waitlist for the book
        waitlistService.addUserToWaitlist(user, bukuId);

        ra.addFlashAttribute("message", "You have been added to the waitlist for this book.");
        return "redirect:/user/buku";
    }

    @GetMapping("/user/waitlist")
    public String viewWaitlist(Model model, HttpSession session) {
        // Get the user ID from the session
        Integer userId = (Integer) session.getAttribute("userId");

        // Retrieve the waitlist for the user from the service
        List<Waitlist> waitlist = waitlistService.getWaitlistByUserId(userId);

        // Add the waitlist to the model
        model.addAttribute("listWaitlist", waitlist);

        return "user/waitlist";
    }

    @GetMapping("/user/waitlist/remove/{waitlistId}")
    public String removeWaitlist(@PathVariable("waitlistId") Integer waitlistId) {
        waitlistService.removeWaitlistById(waitlistId);
        return "redirect:/user/index";
    }






}