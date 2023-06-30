package com.uas.pbo.controller;

import com.uas.pbo.exceptions.InvalidCredentialsException;
import com.uas.pbo.exceptions.UserNotFoundException;
import com.uas.pbo.model.User;
import com.uas.pbo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
    public class LoginController {
    @Autowired
    private UserService service;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/")
    public String showHomePage() {
        return "login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @GetMapping("/users/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/user/register")
    public String registerUser(User user, RedirectAttributes ra) {
        service.save(user);
        ra.addFlashAttribute("message", "User baru sudah dibuat. Silakan login.");
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(String nim, String password, RedirectAttributes ra, HttpServletRequest request, Model model) {
        try {
            HttpSession session = request.getSession();

            User user = service.login(nim, password, session);

            boolean loggedIn = session.getAttribute("userId") != null;
            System.out.println("loggedIn: " + loggedIn);

            model.addAttribute("loggedIn", loggedIn);

            if (user.getAdalah_pustakawan()) {
                return "redirect:/librarian/index";
            } else {
                return "redirect:/user/index";
            }
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        } catch (InvalidCredentialsException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/login"; // Redirect back to the login page with an error message
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) {
        // Clear the session attributes and invalidate the session
        session.removeAttribute("userId");
        session.invalidate();

        // Add a flash attribute for the logout success message
        ra.addFlashAttribute("message", "Logout berhasil.");

        // Redirect the user to the login page
        return "redirect:/login";
    }


    @GetMapping("/buku")
    public String showBuku(Model model) {
        return "/user/buku";
    }

    @GetMapping("/chat")
    public String chatPage() {
        return "chat"; // Return the name of the HTML template for the chat page
    }

}
