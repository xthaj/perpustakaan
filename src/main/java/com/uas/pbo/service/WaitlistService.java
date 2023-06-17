package com.uas.pbo.service;

import com.uas.pbo.model.*;
import com.uas.pbo.repository.UserRepository;
import com.uas.pbo.repository.WaitlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitlistService {

    @Autowired
    private WaitlistRepository waitlistRepository;

    public boolean isUserOnWaitlist(Integer userId, Integer bukuId) {
        User user = new User();
        user.setId(userId);

        return waitlistRepository.existsByUserAndBukuId(user, bukuId);
    }

    public void addUserToWaitlist(User user, Integer bukuId) {
        Buku buku = new Buku();
        buku.setId(bukuId);

        Waitlist waitlist = new Waitlist();
        waitlist.setUser(user);
        waitlist.setBuku(buku);

        waitlistRepository.save(waitlist);
    }

    public List<Waitlist> getWaitlistByUserId(Integer userId) {
        return waitlistRepository.findByUserId(userId);
    }

    public List<Waitlist> getWaitlistByBukuId(Integer bukuId) {
        return waitlistRepository.findByBukuId(bukuId);
    }

    public Waitlist save(Waitlist waitlist) {
        return waitlistRepository.save(waitlist);
    }

    public List<Waitlist> getUpdatedWaitlistsByUserId(Integer userId) {
        return waitlistRepository.findByUserIdAndIsUpdated(userId, true);
    }

    public void removeWaitlistById(Integer waitlistId) {
        waitlistRepository.deleteById(waitlistId);
    }

    public int getTotalWaitlistCount() {
        return waitlistRepository.getTotalCount();
    }

    public List<Waitlist> listAll() {
        return waitlistRepository.findAll();
    }


}
