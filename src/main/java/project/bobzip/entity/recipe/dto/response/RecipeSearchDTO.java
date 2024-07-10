package project.bobzip.entity.recipe.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeSearchDTO {

    private Long recipeId;

    private String recipeName;

    private List<String> availableIngredients = new ArrayList<>();

    private List<String> requiredIngredients = new ArrayList<>();

    protected  RecipeSearchDTO() {}

    public RecipeSearchDTO(Long recipeId, String recipeName) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
    }

    public void addAvailableIngredients(String ingredientName) {
        availableIngredients.add(ingredientName);
    }

    public void addRequiredIngredients(String ingredientName) {
        requiredIngredients.add(ingredientName);
    }
}
