package com.application.gentlegourmet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@NamedEntityGraph(
    name = "cart-graph.customer-product",
    attributeNodes = {
        @NamedAttributeNode("customer"),
        @NamedAttributeNode("product")
    }
)
@NamedEntityGraph(
    name = "cart-graph.product",
    attributeNodes = {
        @NamedAttributeNode("product"),
    }
)
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    ///////////////////////////////////////////////////////////////////////////


}
