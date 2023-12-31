package com.uas.pbo.service;

import com.uas.pbo.exceptions.InvalidCredentialsException;
import com.uas.pbo.exceptions.UserNotFoundException;
import com.uas.pbo.model.User;
import com.uas.pbo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository repo;
    @Autowired private HttpSession session;

    public void save(User user) {
        repo.save(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> result =  repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }

        throw new UserNotFoundException("Could not find any users with ID " + id);
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);

        if((count == null) || (count == 0)) {
            throw new UserNotFoundException("Could not find any users with ID " + id);

        }
        repo.deleteById(id);
    }

    public User login(String nim, String password, HttpSession session) throws UserNotFoundException, InvalidCredentialsException {
        User user = repo.findByNim(nim);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                session.setAttribute("userId", user.getId());
                return user;
            } else {
                throw new InvalidCredentialsException("Password salah!");
            }
        } else {
            throw new UserNotFoundException("NIM kosong atau tidak terdaftar!");
        }
    }

    public String getUserNameById(Integer userId) {
        return repo.getNamaById(userId);
    }

}