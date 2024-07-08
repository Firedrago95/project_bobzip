package project.bobzip.entity.fridge.dto.response;

import lombok.Data;
import lombok.Getter;
import project.bobzip.entity.fridge.entity.FridgeIngredient;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class FridgeIngredientDto {

    private List<String> ingredientNames;

    public FridgeIngredientDto(List<FridgeIngredient> ingredients) {
        List<String> strings = new ArrayList<>();
        for (String string : strings) {
            strings.add(string);
        }
        ingredientNames = strings;
    }
}
