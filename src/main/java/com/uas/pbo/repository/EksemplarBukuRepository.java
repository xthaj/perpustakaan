package com.uas.pbo.repository;

import com.uas.pbo.model.Buku;
import com.uas.pbo.model.EksemplarBuku;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EksemplarBukuRepository extends CrudRepository<EksemplarBuku, Integer> {
    @Query("SELECT COUNT(e) FROM EksemplarBuku e WHERE e.buku.isbn = ?1")
    int countByBukuIsbn(String isbn);

    default EksemplarBuku createEksemplarBukuByIsbn(String isbn) {
        Buku buku = getBookByIsbn(isbn);
        if (buku != null) {
            EksemplarBuku eksemplarBuku = new EksemplarBuku();
            eksemplarBuku.setBuku(buku);
            return save(eksemplarBuku);
        }
        return null;
    }


    @Query("SELECT b FROM Buku b WHERE b.isbn = :isbn")
    Buku getBookByIsbn(@Param("isbn") String isbn);

    @Query("SELECT e FROM EksemplarBuku e WHERE e.buku.isbn = :isbn")
    List<EksemplarBuku> findByBukuIsbn(@Param("isbn") String isbn);

}
