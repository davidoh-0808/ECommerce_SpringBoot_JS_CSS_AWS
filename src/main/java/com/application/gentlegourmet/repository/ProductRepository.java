package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {



}
