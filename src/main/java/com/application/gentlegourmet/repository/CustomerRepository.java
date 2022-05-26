package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {



}
