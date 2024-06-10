package project.bobzip.recipe.repository;

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

    @Test
    void recipeSaveTest() {
        // given
        Recipe kimchiStew = createRecipe("김치찌개", "맛있는 김치찌개를 끓여봅시다!");

        // when
        recipeRepository.save(kimchiStew);

        // then
        Recipe findRecipe = recipeRepository.findById(kimchiStew.getId()).get();
        assertThat(findRecipe).isEqualTo(kimchiStew);
        assertThat(findRecipe.getTitle()).isEqualTo("김치찌개");
        assertThat(findRecipe.getInstruction()).isEqualTo("맛있는 김치찌개를 끓여봅시다!");
    }

    @Test
    @DisplayName("페이징 테스트")
    void findAllTest() {
        // given
        List<Recipe> testRecipes = createTestRecipes();
        for (Recipe testRecipe : testRecipes) {
            recipeRepository.save(testRecipe);
        }

        // when
        Pageable pageable = PageRequest.of(0, 1); // 첫 페이지(0부터시작), 페이지당 1개의 항목
        Page<Recipe> findRecipePages = recipeRepository.findAll(pageable);

        // then
        List<Recipe> contents = findRecipePages.getContent();
        assertThat(contents.size()).isEqualTo(1);
        assertThat(findRecipePages.getNumber()).isEqualTo(0);
        assertThat(findRecipePages.getSize()).isEqualTo(1);
    }

    private static List<Recipe> createTestRecipes() {
        return Arrays.asList(
                createRecipe("김치찌개", "맛있는 김치찌개를 끓여봅시다~"),
                createRecipe("된장찌개", "보글보글 맛있는 된장찌개"),
                createRecipe("제육볶음", "매콤하고 맛있는 밥도둑 제육볶음!")
        );
    }

    private static Recipe createRecipe(String title, String instruction) {
        return Recipe.builder()
                .title(title)
                .instruction(instruction)
                .build();
    }
}