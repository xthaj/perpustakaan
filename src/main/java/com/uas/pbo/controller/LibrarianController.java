package com.uas.pbo.controller;

import com.uas.pbo.exceptions.*;
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

import java.util.List;
import java.util.Optional;

@Controller
public class LibrarianController {
    @Autowired
    private BukuService bukuService;
    @Autowired
    private UserService userService;
    @Autowired
    private WaitlistService waitlistService;
    @Autowired
    private EksemplarBukuService eksemplarBukuService;
    @Autowired
    private PeminjamanService peminjamanService;

    @GetMapping("/librarian/index")
    public String showIndexLibrarian(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        String userName = userService.getUserNameById(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);

        //quick stats
        int peminjamanCount = peminjamanService.getTotalPeminjamanCount();
        int wishlistCount = waitlistService.getTotalWaitlistCount();

        model.addAttribute("peminjamanCount", peminjamanCount);
        model.addAttribute("wishlistCount", wishlistCount);

        return "librarian/dashboard";
    }

    @GetMapping("/librarian/buku")
    public String showBukuLibrarian(Model model) {
        List<Buku> listBuku = bukuService.listAll();
        model.addAttribute("listBuku", listBuku);
        model.addAttribute("eksemplarBukuService", eksemplarBukuService);


        return "librarian/buku";
    }

    @GetMapping("/librarian/buku/create")
    public String showAddBukuForm(Model model) {
        model.addAttribute("buku", new Buku());
        model.addAttribute("pageTitle", "Tambah Buku Baru");

        return "librarian/buku_form";
    }

    @PostMapping("/librarian/buku/save")
    public String saveBook(Buku buku, RedirectAttributes ra) {
        bukuService.save(buku);
        ra.addFlashAttribute("message", "Buku telah disimpan.");
        return "redirect:/librarian/buku";
    }

    @GetMapping("/librarian/buku/{id}/edit")
    public String showEditBukuForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Buku buku = bukuService.get(id);
            model.addAttribute("buku", buku);
            model.addAttribute("pageTitle", "Edit Buku");


            return "librarian/buku_form";
        } catch (BukuNotFoundException e) {
            ra.addFlashAttribute("message", "Buku telah diedit.");
            return "redirect:/librarian/buku";
        }
    }

    @GetMapping("/librarian/buku/{id}/delete")
    public String deleteBook(@PathVariable("id") Integer id, RedirectAttributes ra) {
        bukuService.delete(id);
        ra.addFlashAttribute("message", "Buku berhasil dihapus.");
        return "redirect:/librarian/buku";
    }

    @PostMapping("/librarian/peminjaman/confirm/{id}")
    public String confirmPeminjaman(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) throws PeminjamanNotFoundException {
        peminjamanService.confirmPeminjaman(id);
        redirectAttributes.addFlashAttribute("message", "Peminjaman sudah dikembalikan.");
        return "redirect:/librarian/peminjaman";
    }


    @GetMapping("/librarian/peminjaman")
    public String showPeminjamanLibrarian(Model model) {
        List<Peminjaman> peminjamanList = peminjamanService.listAll();
        model.addAttribute("listPeminjaman", peminjamanList);

        return "librarian/peminjaman";
    }

    @GetMapping("/librarian/waitlist")
    public String showWaitlistLibrarian(Model model) {
        List<Waitlist> waitlist = waitlistService.listAll();
        model.addAttribute("Waitlist", waitlist);

        return "librarian/waitlist";
    }

    @GetMapping("/eksemplar/{isbn}")
    public String showEksemplarStatuses(@PathVariable("isbn") String isbn, Model model) {
        List<EksemplarBuku> listEksemplar = eksemplarBukuService.findByBukuIsbn(isbn);
        model.addAttribute("listEksemplar", listEksemplar);
        return "librarian/eksemplar";
    }

    @GetMapping("/eksemplar/{isbn}/create")
    public String createEksemplarBuku(@PathVariable("isbn") String isbn) {
        eksemplarBukuService.createEksemplarBukuByIsbn(isbn);
        return "redirect:/eksemplar/" + isbn;
    }

    @GetMapping("/eksemplar/{isbn}/delete")
    public String deleteEksemplarBuku(@PathVariable("isbn") String isbn, RedirectAttributes redirectAttributes) {
        eksemplarBukuService.deleteEksemplarBukuByIsbn(isbn, redirectAttributes);
        return "redirect:/librarian/buku";
    }






}