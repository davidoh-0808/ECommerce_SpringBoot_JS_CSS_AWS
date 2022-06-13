package com.application.gentlegourmet;

import com.application.gentlegourmet.entity.*;
import com.application.gentlegourmet.repository.*;
import com.application.gentlegourmet.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class GentlegourmetApplication {

	public static void main(String[] args) {
		SpringApplication.run(GentlegourmetApplication.class, args);
	}

	@Bean
	public CommandLineRunner initialCreate(S3Service s3Service) {
		return(args) -> {
			System.out.println("***************** testing application ********************");



			System.out.println("***************** testing application ********************");
		};
	}

}



