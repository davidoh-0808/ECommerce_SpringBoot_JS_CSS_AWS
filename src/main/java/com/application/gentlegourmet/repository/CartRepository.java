package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Cart;
import com.application.gentlegourmet.entity.Customer;
import com.application.gentlegourmet.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {


    @EntityGraph(
        value = "cart-graph.customer-product",
        type = EntityGraph.EntityGraphType.LOAD
    )
    @Query("SELECT c FROM Cart c WHERE c.customer = ?1 and c.product = ?2")
    Cart findCartByCustomerAndProduct(Customer customer, Product product);


    @EntityGraph(
        value = "cart-graph.product",
        type = EntityGraph.EntityGraphType.LOAD
    )
    @Query("SELECT c FROM Cart c WHERE c.customer = ?1")
    List<Cart> findCartsByCustomer(Customer customer);


    @Transactional
    @Modifying
    @Query("DELETE FROM Cart c where c.customer = ?1")
    void removeAllCartsByCustomer(Customer customer);

}
