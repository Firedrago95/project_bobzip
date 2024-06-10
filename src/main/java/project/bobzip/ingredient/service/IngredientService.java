package project.bobzip.ingredient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.ingredient.entity.Ingredient;
import project.bobzip.ingredient.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;


    @Transactional
    public List<Ingredient> addIngredients(List<String> ingredientNames) {
        // 재료명 중복 검사 및 재료 등록
        List<Ingredient> ingredients = new ArrayList<>();
        for (String ingredientName : ingredientNames) {
            if (!ingredientRepository.existsByName(ingredientName)) {
                Ingredient newIngredient = new Ingredient(ingredientName);
                ingredientRepository.save(newIngredient);
                ingredients.add(newIngredient);
            } else {
                Ingredient oldIngredient = ingredientRepository.findByName(ingredientName).get();
                ingredients.add(oldIngredient);
            }
        }
        return ingredients;
    }
}
