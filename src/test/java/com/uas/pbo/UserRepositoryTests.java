package com.uas.pbo;

import com.uas.pbo.model.User;
import com.uas.pbo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setNim("222111933");
        user.setPassword("machine_gun");
        user.setNama("Atha Jr");
        user.setAdalah_pustakawan(false);

        User savedUser = repo.save(user);

        Assertions.assertNotNull(savedUser.getId(), "User ID should not be null after saving");
//        Assertions.assertEquals("222111930", savedUser.getNim(), "NIM should match");
//        Assertions.assertEquals("machine_gun", savedUser.getPassword(), "Password should match");
//        Assertions.assertEquals("Atha Sr", savedUser.getNama(), "Nama should match");
//        Assertions.assertTrue(savedUser.isAdalah_pustakawan(), "isAdalah_pustakawan should be true");
    }

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setPassword("1");
        repo.save(user);

        User updatedUser = repo.findById(userId).get();
        Assertions.assertEquals("Mew", updatedUser.getPassword(), "Password should match");
    }

    @Test
    public void testGet() {
        Integer userId = 2;
        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertTrue(optionalUser.isPresent(), "User should be present");
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Integer userId = 2;
        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertFalse(optionalUser.isPresent(), "User should be present");

    }
}
