package com.application.gentlegourmet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@NamedEntityGraph(
    name = "recipe-graph.name-product",
    attributeNodes = {
        @NamedAttributeNode("name"),
        @NamedAttributeNode("product")
    }
)
@NamedEntityGraph(
    name = "recipe-graph.all-fields-except-details",
    attributeNodes = {
            @NamedAttributeNode("name"),
            @NamedAttributeNode("created"),
            @NamedAttributeNode("viewed"),
            @NamedAttributeNode("product"),
            @NamedAttributeNode("recipeImages")
    }
)
@NamedEntityGraph(
    name = "recipe-graph.all-fields",
    attributeNodes = {
        @NamedAttributeNode("name"),
        @NamedAttributeNode("ingredients"),
        @NamedAttributeNode("serving"),
        @NamedAttributeNode("prep"),
        @NamedAttributeNode("cook"),
        @NamedAttributeNode("created"),
        @NamedAttributeNode("viewed"),
        @NamedAttributeNode("product"),
        @NamedAttributeNode("recipeImages")
    }
)
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "ingredients", length = 255, nullable = false)
    private String ingredients;

    @Column(name = "serving", nullable = false)
    private int serving;

    @Column(name = "prep", nullable = false)
    private int prep;

    @Column(name = "cook", nullable = false)
    private int cook;

    @Column(name = "created", nullable = false)
    @CreationTimestamp
    private Date created;

    @Column(name = "viewed", nullable = false)
    private int viewed;

    //no column needed here (filled as needed in RecipeService)
    private String recipeThumbnailPath;

    ///////////////////////////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RecipeImage> recipeImages;

    ///////////////////////////////////////////////////////////////////////////

    public Recipe(String ingredients, int serving, int prep, int cook, int viewed) {
        this.ingredients = ingredients;
        this.serving = serving;
        this.prep = prep;
        this.cook = cook;
        this.viewed = viewed;
    }
}
