package project.bobzip.entity.fridge.dto.response;

import lombok.Data;
import lombok.Getter;
import project.bobzip.entity.fridge.entity.FridgeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
public class FridgeIngredientDto {

    private List<String> ingredientNames;

    public FridgeIngredientDto(List<FridgeIngredient> ingredients) {
        List<String> names = ingredients.stream()
                .map(fi -> fi.getIngredient().getName())
                .collect(Collectors.toList());
        this.ingredientNames = names;
    }
}
