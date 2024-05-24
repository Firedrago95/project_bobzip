package project.bobzip.recipe.entity;

import lombok.Getter;

@Getter
public enum IngredientCategory {
    CONDIMENT("condiment","조미료"),
    VEGETABLE("vegetable","채소"),
    MEAT("meat","육류"),
    FRUIT("fruit","과일"),
    GRAIN("grain","곡물"),
    SEAFOOD("seafood","해산물"),
    MUSHROOM("mushroom","버섯");

    private final String code;
    private final String categoryName;

    IngredientCategory(String code ,String categoryName) {
        this.code = code;
        this.categoryName = categoryName;
    }
}
