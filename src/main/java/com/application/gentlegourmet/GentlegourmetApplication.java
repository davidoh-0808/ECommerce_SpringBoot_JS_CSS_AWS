package com.application.gentlegourmet;

import com.application.gentlegourmet.entity.TestUser;
import com.application.gentlegourmet.service.TestUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
public class GentlegourmetApplication {

	public static void main(String[] args) {
		SpringApplication.run(GentlegourmetApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner initialCreate(TestUserService testUserService) {
		return(args) -> {

			TestUser testUser = new TestUser("test-username-123", "test-pw-123");
			System.out.println("********** saving a TestUser Entity : " + testUser);
			testUserService.createTestUser(testUser);

			System.out.println("********** finding all & printing TestUser Entities");
			List<TestUser> testUsers = testUserService.findAllTestUsers();
			for(TestUser t : testUsers) {
				System.out.println(t);
			}

		};
	}
	 */


}
