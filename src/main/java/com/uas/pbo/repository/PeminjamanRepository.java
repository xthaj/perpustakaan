package com.uas.pbo.repository;

import com.uas.pbo.model.Peminjaman;
import org.springframework.data.repository.CrudRepository;

import com.uas.pbo.model.User;

public interface PeminjamanRepository extends CrudRepository<Peminjaman, Integer> {
}
