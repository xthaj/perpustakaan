package com.uas.pbo.service;

import com.uas.pbo.model.Buku;
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

        public Optional<Buku> findBukuById(Integer id) {
            return repo.findById(id);
        }

        public void deleteBukuById(Integer id) {
            repo.deleteById(id);
        }




}
