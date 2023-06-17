package com.uas.pbo.controller;

<<<<<<< HEAD
import com.uas.pbo.exceptions.BukuNotFoundException;
import com.uas.pbo.exceptions.PeminjamanNotFoundException;
import com.uas.pbo.exceptions.UserNotFoundException;
import com.uas.pbo.model.*;
import com.uas.pbo.service.BukuService;
import com.uas.pbo.service.EksemplarBukuService;
import com.uas.pbo.service.PeminjamanService;
import com.uas.pbo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
=======
>>>>>>> parent of 3ffd5e7 (why is my last commit not committing)
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {
    @GetMapping("/user/index")
    public String showIndexUser(Model model) {
        return "/user/index";
    }

<<<<<<< HEAD
    @GetMapping("/user/buku")
    public String exploreBooks(Model model) {
        List<Buku> listBuku = bukuService.listAll();
        model.addAttribute("listBuku", listBuku);
        model.addAttribute("eksemplarBukuService", eksemplarBukuService);

        return "user/buku";
    }

    @GetMapping("/user/peminjaman")
    public String viewPeminjaman(Model model, HttpSession session) throws PeminjamanNotFoundException {
        // Get the user ID from the session
        Integer userId = (Integer) session.getAttribute("userId");

        // Retrieve the list of Peminjaman for the user ID
        List<Peminjaman> peminjamans = peminjamanService.listByUserId(userId);

        // Add the list of Peminjaman to the model
        model.addAttribute("listPeminjaman", peminjamans);

        return "user/peminjaman";
    }

    @GetMapping("/buku/{isbn}/pinjam")
    public String createPeminjaman(@PathVariable("isbn") String isbn, RedirectAttributes ra, HttpSession session) throws UserNotFoundException, BukuNotFoundException {
        Integer userId = (Integer) session.getAttribute("userId");
        User user = userService.get(userId);

        // Find an available EksemplarBuku for the given bukuId
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



    @GetMapping("/user/waitlist")
    public String viewWaitlist(Model model) {
        // Add your logic here
        return "user/waitlist"; // Assuming "user/waitlist" is the view template
    }




=======
>>>>>>> parent of 3ffd5e7 (why is my last commit not committing)
}
