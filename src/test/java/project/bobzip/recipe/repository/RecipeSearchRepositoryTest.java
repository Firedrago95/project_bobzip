package project.bobzip.recipe.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.repository.RecipeRepository;
import project.bobzip.entity.recipe.repository.RecipeSearchRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Profile("prod")
class RecipeSearchRepositoryTest {

    @Autowired
    RecipeSearchRepository recipeSearchRepository;
    @Autowired
    RecipeRepository recipeRepository;

    @Test
    void searchRecipesTest() {
        // given
        List<Recipe> testRecipes = Arrays.asList(
                createRecipe("김치찌개", "맛있는 김치찌개"),
                createRecipe("된장찌개", "보글보글 된장찌개"),
                createRecipe("고추장찌개", "고추장찌개는 맛있다.")
        );
        recipeRepository.saveAll(testRecipes);
        PageRequest pageRequest = PageRequest.of(0, 1);

        // when
        Page<Recipe> result = recipeSearchRepository.searchRecipes("김치", pageRequest);

        // then
        List<Recipe> findRecipes = result.getContent();
        assertThat(findRecipes.size()).isEqualTo(1);
        assertThat(findRecipes.get(0).getTitle()).isEqualTo("김치찌개");

    }

    private static Recipe createRecipe(String title, String instuction) {
        return Recipe.builder()
                .title(title)
                .instruction(instuction)
                .build();
    }

}