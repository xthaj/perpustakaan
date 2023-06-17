package com.uas.pbo.repository;

import com.uas.pbo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    public long countById(Integer id);

    public User findByNim(String nim);

}
