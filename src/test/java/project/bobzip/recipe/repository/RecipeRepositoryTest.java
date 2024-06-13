package project.bobzip.recipe.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.recipe.entity.Recipe;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RecipeRepositoryTest {

    @Autowired
    RecipeRepository recipeRepository;

    Recipe testRecipe;
    List<Recipe> testRecipes;

    @BeforeEach
    void initTestData() {
        testRecipe = createRecipe("김치찌개", "맛있는 김치찌개를 끓여봅시다!");
        testRecipes = Arrays.asList(
                createRecipe("짜파게티", "오늘은 내가 짜파게티 요리사~"),
                createRecipe("된장찌개", "보글보글 맛있는 된장찌개"),
                createRecipe("제육볶음", "매콤하고 맛있는 밥도둑 제육볶음!"));

        recipeRepository.save(testRecipe);
        recipeRepository.saveAll(testRecipes);
    }

    @Test
    void recipeSaveTest() {
        // when
        recipeRepository.save(testRecipe);

        // then
        Recipe findRecipe = recipeRepository.findById(testRecipe.getId()).get();
        assertThat(findRecipe).isEqualTo(testRecipe);
        assertThat(findRecipe.getTitle()).isEqualTo("김치찌개");
        assertThat(findRecipe.getInstruction()).isEqualTo("맛있는 김치찌개를 끓여봅시다!");
    }

    @Test
    @DisplayName("페이징 테스트")
    void findAllTest() {
        // when
        Pageable pageable = PageRequest.of(0, 1); // 첫 페이지(0부터시작), 페이지당 1개의 항목
        Page<Recipe> findRecipePages = recipeRepository.findAll(pageable);

        // then
        List<Recipe> contents = findRecipePages.getContent();
        assertThat(contents.size()).isEqualTo(1);
        assertThat(findRecipePages.getNumber()).isEqualTo(0);
        assertThat(findRecipePages.getSize()).isEqualTo(1);
    }

    @Test
    void findByIdTest() {
        // when
        Recipe findRecipe = recipeRepository.findById(testRecipe.getId()).get();

        // then
        assertThat(testRecipe).isEqualTo(findRecipe);
    }

    private static Recipe createRecipe(String title, String instruction) {
        return Recipe.builder()
                .title(title)
                .instruction(instruction)
                .build();
    }
}