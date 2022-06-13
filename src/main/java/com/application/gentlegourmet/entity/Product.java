package com.application.gentlegourmet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@NamedEntityGraph(
    name = "product-graph.name-price-category",
    attributeNodes = {
        @NamedAttributeNode("name"),
        @NamedAttributeNode("price"),
        @NamedAttributeNode("category"),
    }
)
@NamedEntityGraph(
    name = "product-graph.all-fields",
    attributeNodes = {
        @NamedAttributeNode("id"),
        @NamedAttributeNode("name"),
        @NamedAttributeNode("description"),
        @NamedAttributeNode("price"),
        @NamedAttributeNode("category"),
        @NamedAttributeNode("brand"),
        @NamedAttributeNode("productReviews"),
        @NamedAttributeNode("productImages"),
    }
)
@NamedEntityGraph(
    name = "product-graph.id-name",
    attributeNodes = {
        @NamedAttributeNode("id"),
        @NamedAttributeNode("name"),
    }
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

    //@Transient fields are NOT DB columns. They are temp fields for DTO functionalities between view and MVC
    //@Transient fields for printing out product details
    @Transient
    private String productThumbnailPath;
    @Transient
    private List<String> productDescriptions;
    @Transient
    private Map<String, Integer> ratingMap;

    //@Transient fields for uploading new product
    @Transient
    private String categoryName;
    @Transient
    private String brandName;
    @Transient
    private List<MultipartFile> multipartFiles;
    @Transient
    private String productTagString;

    ///////////////////////////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "brand_id", nullable = true)
    private Brand brand;

    @OneToOne(fetch = FetchType.LAZY)
    private Cart cart;

    @OneToOne(fetch = FetchType.LAZY)
    private PurchaseDetail purchaseDetail;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductReview> productReviews;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductTag> productTags;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Recipe> recipes;

    ///////////////////////////////////////////////////////////////////////////

    public Product(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
