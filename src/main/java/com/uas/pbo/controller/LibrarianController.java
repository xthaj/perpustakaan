package com.uas.pbo.controller;

import com.uas.pbo.exceptions.BukuNotFoundException;
import com.uas.pbo.model.Buku;
import com.uas.pbo.model.EksemplarBuku;
import com.uas.pbo.model.User;
import com.uas.pbo.service.BukuService;
import com.uas.pbo.service.EksemplarBukuService;
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
    private EksemplarBukuService eksemplarBukuService;

    @GetMapping("/librarian/index")
    public String showIndexLibrarian(Model model) {
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
        ra.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/librarian/buku";
    }

    @GetMapping("/librarian/buku/{id}/edit")
    public String showEditBukuForm(Model model) {
        model.addAttribute("buku", new Buku());
        model.addAttribute("pageTitle", "Tambah Buku Baru");

        return "buku_form";
    }

    @GetMapping("/librarian/edit/{isbn}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        Optional<Buku> buku = bukuService.findBukuById(id);
        model.addAttribute("buku", buku);
        model.addAttribute("pageTitle", "Edit Buku");
        return "buku_form";
    }




    @GetMapping("/librarian/peminjaman")
    public String showPeminjamanLibrarian(Model model) {
        return "librarian/peminjaman";
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
