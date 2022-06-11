package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query("SELECT c FROM Customer c WHERE c.username = ?1")
    Customer findByUsername(String username);


}
