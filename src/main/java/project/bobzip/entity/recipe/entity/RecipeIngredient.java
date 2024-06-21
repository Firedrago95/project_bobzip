package project.bobzip.entity.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import project.bobzip.entity.ingredient.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class RecipeIngredient {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "quantity")
    private int quantity;

    @Column(length = 10)
    private String unit;

    //==생성 메서드==//
    public RecipeIngredient() {}

    public static List<RecipeIngredient> createRecipeIngredient(List<Ingredient> ingredients,
                                                                List<Integer> quantities,
                                                                List<String> units) {
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.ingredient = ingredients.get(i);
            recipeIngredient.unit = units.get(i);
            recipeIngredient.quantity = quantities.get(i);
            recipeIngredients.add(recipeIngredient);
        }
        return recipeIngredients;
    }


    //==연관관계 메서드==//
    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}
