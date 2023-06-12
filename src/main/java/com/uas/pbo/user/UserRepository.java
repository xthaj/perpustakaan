package com.uas.pbo.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    public long countById(Integer id);

    public User findByNim(String nim);

}
