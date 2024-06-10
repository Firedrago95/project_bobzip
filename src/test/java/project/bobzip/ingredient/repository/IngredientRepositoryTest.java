package project.bobzip.ingredient.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.ingredient.entity.Ingredient;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepository;

    @BeforeEach
    void initData() {
        addIngredient("김치");
        addIngredient("돼지고기");
    }

    @Test
    void existsByNameTest() {
        // when
        boolean hasKimchi = ingredientRepository.existsByName("김치");
        boolean hasPork = ingredientRepository.existsByName("돼지고기");
        boolean hasBeef = ingredientRepository.existsByName("소고기");

        // then
        assertThat(hasKimchi).isTrue();
        assertThat(hasPork).isTrue();
        assertThat(hasBeef).isFalse();
    }

    @Test
    void findByNameTest() {
        // when
        Ingredient findKimchi = ingredientRepository.findByName("김치").get();
        Ingredient findPork = ingredientRepository.findByName("돼지고기").get();

        // then
        assertThat(findKimchi.getName()).isEqualTo("김치");
        assertThat(findPork.getName()).isEqualTo("돼지고기");
    }

    private void addIngredient(String name) {
        Ingredient ingredient = new Ingredient(name);
        ingredientRepository.save(ingredient);
    }
}