package project.bobzip.entity.recipe.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RecipeSearchDTO {

    private Long recipeId;

    private String recipeName;

    private List<String> ingredients;

    protected  RecipeSearchDTO() {}

    public RecipeSearchDTO(Long recipeId, String recipeName, List<String> ingredients) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
    }
}
