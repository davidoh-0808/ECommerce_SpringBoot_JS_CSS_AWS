package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Purchase;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    /*
    @EntityGraph(
        value = "purchase-graph.id-created-customer",
        type = EntityGraph.EntityGraphType.LOAD
    )
    @Query("SELECT pr FROM Purchase pr where Purchase = :purchase")
    Purchase findPurchaseByProductReview(@Param("purchase") Purchase purchase);
     */

}
