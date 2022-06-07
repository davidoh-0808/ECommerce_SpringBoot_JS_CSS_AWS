package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //add the following method for Spring Security ->
    @Query("SELECT c FROM Customer c WHERE c.username = ?1")
    Customer findByUsername(String username);

    /*
    @Query("SELECT c.username FROM Customer c where Customer = :customer")
    String findCustomerUsernameByCustomer(@Param("customer") Customer customer);
     */




}
