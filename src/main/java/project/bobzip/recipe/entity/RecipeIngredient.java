package project.bobzip.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RecipeIngredient {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;
}
