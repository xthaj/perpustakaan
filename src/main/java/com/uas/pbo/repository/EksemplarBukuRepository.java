package com.uas.pbo.repository;

import com.uas.pbo.model.EksemplarBuku;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EksemplarBukuRepository extends CrudRepository<EksemplarBuku, Integer> {
    @Query("SELECT COUNT(e) FROM EksemplarBuku e WHERE e.buku.isbn = ?1")
    int countByBukuIsbn(String isbn);
}
