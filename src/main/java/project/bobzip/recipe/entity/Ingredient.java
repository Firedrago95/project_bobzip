package project.bobzip.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Ingredient {

    @Id
    @GeneratedValue
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_id", length = 10)
    private IngredientCategory category;
}
