package com.uas.pbo.repository;

import com.uas.pbo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {
    public long countById(Integer id);

    @Query("SELECT u.nama FROM User u WHERE u.id = :id")
    String getNamaById(@Param("id") Integer id);

    public User findByNim(String nim);

}
