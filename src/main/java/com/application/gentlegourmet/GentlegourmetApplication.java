package com.application.gentlegourmet;

import com.application.gentlegourmet.entity.*;
import com.application.gentlegourmet.repository.*;
import com.application.gentlegourmet.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class GentlegourmetApplication {

	public static void main(String[] args) {
		SpringApplication.run(GentlegourmetApplication.class, args);
	}

	@Bean
	public CommandLineRunner initialCreate(ProductService productService, CategoryRepository categoryRepository, BrandRepository brandRepository, ProductRepository productRepository) {
		return(args) -> {
			System.out.println("***************** testing repo ********************");
			/*
			Product product1 = new Product();
			product1.setName("Beyond Beef 비욘드 비프");
			product1.setDescription("설명: 맛있는 비건 비프로 건강과 맛을 모두 챙기세요, 맛있는 비건 비프로 건강과 맛을 모두 챙기세요");
			product1.setPrice(12000);

			Optional<Category> category1 = categoryRepository.findById( Long.valueOf(1) );
			List<Category> category2 = categoryRepository.findAll();
			product1.setCategory(category1.get());

			Optional<Brand> brand1 = brandRepository.findById(3L);
			product1.setBrand(brand1.get());

			productRepository.save(product1);
			*/

			Product productTest = productService.findProductById(2L);
			System.out.println("\n:" + productTest);
			System.out.println(productTest.getName());
			System.out.println(productTest.getDescription());
			System.out.println(productTest.getCategory());

//
//
//			System.out.println(product1.getBrand());
//			System.out.println(product1.getName());
//			System.out.println(product1.getDescription());
			System.out.println("***************** testing repo ********************");
		};
	}

}
