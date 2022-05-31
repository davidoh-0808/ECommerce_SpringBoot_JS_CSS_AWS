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
	public CommandLineRunner initialCreate(CartService cartService, CartRepository cartRepository, CustomerRepository customerRepository,ProductRepository productRepository) {
		return(args) -> {
			System.out.println("***************** testing repo ********************");


			Customer customer = customerRepository.findByUsername("veganlife123");
			Product product = productRepository.findById(2L).get();
			/*
			Cart newCart = new Cart();
			newCart.setCustomer(customer);
			newCart.setProduct(product);
			newCart.setQuantity(1);
			cartService.createCart(newCart);
			 */

			Cart cart = cartRepository.findCartByCustomerAndProduct(customer, product);
			System.out.println("***************** cart : " + cart.getCustomer());
			System.out.println("***************** cart : " + cart.getProduct());
			System.out.println("***************** cart : " + cart.getQuantity());

			System.out.println("***************** testing repo ********************");
		};
	}

}
