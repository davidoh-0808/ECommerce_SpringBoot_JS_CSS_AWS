package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.TestUser;
import com.application.gentlegourmet.repository.TestUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestUserService {

    private final TestUserRepository testUserRepository;

    public List<TestUser> findAllTestUsers() {
        return testUserRepository.findAll();
    }

    public void createTestUser(TestUser testUser) {
        testUserRepository.save(testUser);
    }


}
