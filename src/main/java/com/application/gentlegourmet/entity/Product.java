package com.application.gentlegourmet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@NamedEntityGraph(
    name = "product-graph.category",
    attributeNodes = {
        @NamedAttributeNode("category"),

    }
)
@NamedEntityGraph(
        name = "product-graph.brand",
        attributeNodes = { @NamedAttributeNode("brand") }
)
@NamedEntityGraph(
        name = "product-graph.productReviews",
        attributeNodes = { @NamedAttributeNode("productReviews") }
)
@NamedEntityGraph(
        name = "product-graph.productImages",
        attributeNodes = { @NamedAttributeNode("productImages") }
)
@NamedEntityGraph(
        name = "product-graph.productTags",
        attributeNodes = { @NamedAttributeNode("productTags") }
)
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    ///////////////////////////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToOne
    private Cart cart;

    @OneToOne
    private PurchaseDetail purchaseDetail;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductReview> productReviews;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductTag> productTags;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recipe> recipes;

    ///////////////////////////////////////////////////////////////////////////

    public Product(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
