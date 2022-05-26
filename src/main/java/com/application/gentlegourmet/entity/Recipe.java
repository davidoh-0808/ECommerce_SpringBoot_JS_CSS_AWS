package com.application.gentlegourmet.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "view", nullable = false)
    private int view;

    ///////////////////////////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecipeImage> recipeImages;

    ///////////////////////////////////////////////////////////////////////////


}
