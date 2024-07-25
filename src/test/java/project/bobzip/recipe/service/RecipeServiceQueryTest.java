package project.bobzip.recipe.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.repository.RecipeRepository;
import project.bobzip.entity.recipe.service.RecipeSearchService;
import project.bobzip.entity.recipe.service.RecipeService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Profile("prod")
class RecipeServiceQueryTest {

    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeSearchService recipeSearchService;
    @Autowired
    RecipeRepository recipeRepository;


    @Test
    void findAllRecipesTest() {
        // given
        List<Recipe> testRecipes = Arrays.asList(
                createRecipe("짜파게티", "오늘은 내가 짜파게티 요리사~"),
                createRecipe("된장찌개", "보글보글 맛있는 된장찌개"),
                createRecipe("제육볶음", "매콤하고 맛있는 밥도둑 제육볶음!"));
        recipeRepository.saveAll(testRecipes);

        // when
        Pageable pageable = PageRequest.of(0, 1);
        Page<Recipe> page = recipeService.findAllRecipes(pageable);

        // then
        int totalPages = page.getTotalPages();
        int currentPage = page.getNumber();
        List<Recipe> content = page.getContent();

        assertThat(totalPages).isEqualTo(3);
        assertThat(currentPage).isEqualTo(0);
        assertThat(content.size()).isEqualTo(1);
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

    @Test
    void searchRecipeTest() {
        // given
        List<Recipe> testRecipes = Arrays.asList(
                createRecipe("김치찌개", "맛있는 김치찌개"),
                createRecipe("된장찌개", "보글보글 된장찌개"),
                createRecipe("고추장찌개", "고추장찌개는 맛있다.")
        );
        recipeRepository.saveAll(testRecipes);
        PageRequest pageRequest = PageRequest.of(0, 1);

        // when
        Page<Recipe> result = recipeSearchService.searchRecipe("김치", pageRequest);

        // then
        List<Recipe> findRecipes = result.getContent();
        assertThat(findRecipes.size()).isEqualTo(1);
        assertThat(findRecipes.get(0).getTitle()).isEqualTo("김치찌개");
    }

    private static Recipe createRecipe(String title, String instruction) {
        return Recipe.builder()
                .title(title)
                .instruction(instruction)
                .build();
    }
}