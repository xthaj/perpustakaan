package com.uas.pbo.repository;

import com.uas.pbo.model.Buku;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BukuRepository extends CrudRepository<Buku, Integer> {
    Buku findByIsbn(String isbn);

    Optional<Buku> findById(Integer id);

    void deleteById(Integer id);

}
