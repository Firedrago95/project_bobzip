package project.bobzip.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Ingredient {

    @Id
    @GeneratedValue
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public static List<Ingredient> createIngredients(List<String> names) {
        List<Ingredient> ingredients = new ArrayList<>();
        names.forEach(name -> ingredients.add(new Ingredient(name)));
        return ingredients;
    }
}
