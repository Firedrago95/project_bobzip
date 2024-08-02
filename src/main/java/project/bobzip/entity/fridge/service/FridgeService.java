package project.bobzip.entity.fridge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.fridge.entity.FridgeIngredient;
import project.bobzip.entity.fridge.repository.FridgeRepository;
import project.bobzip.entity.ingredient.entity.Ingredient;
import project.bobzip.entity.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FridgeService {

    private final FridgeRepository fridgeRepository;

    public List<FridgeIngredient> findAll(Member loginMember) {
        return fridgeRepository.findAllIngredients(loginMember);
    }

    @Transactional
    public void addFridgeIngredient(List<Ingredient> ingredientNames, Member loginMember) {
        fridgeRepository.deleteAllByMember(loginMember);

        List<FridgeIngredient> ingredients = ingredientNames.stream()
                .map(ingredient -> new FridgeIngredient(ingredient, loginMember))
                .collect(Collectors.toList());
        fridgeRepository.saveAll(ingredients);
    }
}
