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
@Table(name = "recipe_image")
public class RecipeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path", nullable = false)
    private String path;

    ///////////////////////////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Recipe recipe;

    ///////////////////////////////////////////////////////////////////////////

    public RecipeImage(String path, Recipe recipe) {
        this.path = path;
        this.recipe = recipe;
    }

}
