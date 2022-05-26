package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {



}
