package project.bobzip.recipe.entity;

import lombok.Getter;

@Getter
public enum IngredientCategory {
    CONDIMENT("조미료"),
    VEGETABLE("채소"),
    MEAT("육류"),
    FRUITS("과일"),
    GRAIN("곡물"),
    SEAFOOD("해산물"),
    MUSHROOM("버섯");

    private final String categoryName;

    IngredientCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
