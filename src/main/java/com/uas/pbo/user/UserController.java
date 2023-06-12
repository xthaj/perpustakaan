package com.uas.pbo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
    public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/index")
    public String showLoginBoot(Model model) {
        return "index";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add New User");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {
        service.save(user);
        ra.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User");
            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", "The user has been saved successfully.");
            return "redirect:/users";
        }

    }

    @GetMapping("/users/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/users";

    }

    @PostMapping("/users/login")
    public String login(String nim, String password, RedirectAttributes ra) {
        try {
            User user = service.login(nim, password);
            // Successful login, perform any additional actions here
            return "redirect:/index"; // Redirect to the desired page after successful login
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        } catch (InvalidCredentialsException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/"; // Redirect back to the login page with an error message
    }



}
