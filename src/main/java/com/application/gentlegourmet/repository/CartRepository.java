package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Cart;
import com.application.gentlegourmet.entity.Customer;
import com.application.gentlegourmet.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("SELECT c FROM Cart c WHERE c.customer = ?1 and c.product = ?2")
    @EntityGraph(
        value = "cart-graph.customer-product",
        type = EntityGraph.EntityGraphType.LOAD
    )
    Cart findCartByCustomerAndProduct(Customer customer, Product product);


    @Query("SELECT c FROM Cart c WHERE c.customer = ?1")
    @EntityGraph(
        value = "cart-graph.product",
        type = EntityGraph.EntityGraphType.LOAD
    )
    List<Cart> findCartsByCustomer(Customer customer);


}
