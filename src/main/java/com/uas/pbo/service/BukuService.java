package com.uas.pbo.service;

import com.uas.pbo.exceptions.BukuNotFoundException;
import com.uas.pbo.exceptions.UserNotFoundException;
import com.uas.pbo.model.Buku;
import com.uas.pbo.model.User;
import com.uas.pbo.repository.BukuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BukuService {
    @Autowired
    private BukuRepository repo;
        public List<Buku> listAll() {
            return (List<Buku>) repo.findAll();
        }

        public void save(Buku buku) {
            repo.save(buku);
        }

        public Buku get(Integer id) throws BukuNotFoundException {
            Optional<Buku> result = repo.findById(id);
            if (result.isPresent()) {
                return result.get();
            }

            throw new BukuNotFoundException("Couldn't find that book!");
        }

        public void delete(Integer id) {
            repo.deleteById(id);
        }




}
