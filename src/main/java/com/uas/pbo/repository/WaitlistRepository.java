package com.uas.pbo.repository;

import com.uas.pbo.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WaitlistRepository extends CrudRepository<Waitlist, Integer> {

    boolean existsByUserAndBukuId(User user, Integer bukuId);

    List<Waitlist> findByUserId(Integer userId);

    List<Waitlist> findAll();

    List<Waitlist> findByBukuId(Integer bukuId);

    List<Waitlist> findByUserIdAndIsUpdated(Integer userId, boolean isUpdated);

    void deleteById(Integer waitlistId);

    @Query("SELECT COUNT(w) FROM Waitlist w")
    int getTotalCount();



}
