package project.bobzip.ingredient.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.ingredient.entity.Ingredient;
import project.bobzip.entity.ingredient.repository.IngredientRepository;
import project.bobzip.entity.ingredient.service.IngredientService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Profile("prod")
class IngredientServiceTest {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    @DisplayName("새로운 재료들만 추가")
    void addIngredientsWithNewIngredients() {
        // given
        List<String> ingredientNames = List.of("김치", "돼지고기", "간장");

        // when
        ingredientService.addIngredients(ingredientNames);
        List<Ingredient> result = ingredientService.addIngredients(ingredientNames);

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.stream()
                .map(Ingredient::getName)
                .toList())
                .containsExactlyInAnyOrder("김치", "돼지고기", "간장");
    }

    @Test
    @DisplayName("기존재료와 새로운 재료를 함께 추가")
    void addIngredientsWithExistingIngredients() {
        // given
        ingredientRepository.save(new Ingredient("김치"));
        ingredientRepository.save(new Ingredient("돼지고기"));

        // when
        List<Ingredient> result = ingredientService.addIngredients(List.of("김치", "돼지고기", "소금"));

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.stream()
                .map(Ingredient::getName)
                .toList())
                .containsExactlyInAnyOrder("김치", "돼지고기", "소금");
    }
}