package project.bobzip.recipe.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.recipe.entity.Recipe;
import project.bobzip.recipe.repository.RecipeRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecipeServiceTest {

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    void addRecipeTest() {
        // given


        // when


        // then
    }

    @Test
    void findAllRecipesTest() {
        // given
        List<Recipe> testRecipes = Arrays.asList(
                createRecipe("짜파게티", "오늘은 내가 짜파게티 요리사~"),
                createRecipe("된장찌개", "보글보글 맛있는 된장찌개"),
                createRecipe("제육볶음", "매콤하고 맛있는 밥도둑 제육볶음!"));
        recipeRepository.saveAll(testRecipes);

        // when
        Pageable pageable = PageRequest.of(0, 2);
        List<Recipe> pagingRecipes = recipeService.findAllRecipes(pageable);

        // then
        assertThat(pagingRecipes.size()).isEqualTo(2);
    }

    @Test
    void findRecipeTest() {
        // given
        Recipe testRecipe = createRecipe("김치찌개", "맛있는 김치찌개를 끓여봅시다!");
        recipeRepository.save(testRecipe);

        // when
        Recipe findRecipe = recipeService.findRecipe(testRecipe.getId());

        // then
        assertThat(findRecipe).isEqualTo(testRecipe);
    }

    private static Recipe createRecipe(String title, String instruction) {
        return Recipe.builder()
                .title(title)
                .instruction(instruction)
                .build();
    }
}