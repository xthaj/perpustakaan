package com.uas.pbo.repository;

import com.uas.pbo.model.Peminjaman;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uas.pbo.model.User;

import java.util.List;

public interface PeminjamanRepository extends CrudRepository<Peminjaman, Integer> {
    List<Peminjaman> findByUserId(Integer userId);

    @Query("SELECT COUNT(p) FROM Peminjaman p")
    int getTotalCount();
}
