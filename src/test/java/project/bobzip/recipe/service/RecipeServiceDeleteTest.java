package project.bobzip.recipe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.member.repository.MemberRepository;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.exception.UnauthorizedAccessException;
import project.bobzip.entity.recipe.repository.RecipeRepository;
import project.bobzip.entity.recipe.service.RecipeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class RecipeServiceDeleteTest {

    @Autowired
    RecipeService recipeService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RecipeRepository recipeRepository;

    Member writer;
    Member noAuthorizer;

    @BeforeEach
    void init() {
        writer = new Member("test", "123", "testMember");
        noAuthorizer = new Member("new", "233", "newbie");
        memberRepository.save(writer);
        memberRepository.save(noAuthorizer);
    }

    @Test
    void deleteRecipeTest() {
        // given
        Recipe testRecipe = createRecipeWithWriter("김치찌개", "맛있는 김치찌개를 끓여봅시다!", writer);
        recipeRepository.save(testRecipe);

        // when
        recipeService.deleteRecipe(testRecipe.getId(), writer);

        // then
        Recipe findRecipe = recipeService.findRecipe(testRecipe.getId());
        assertThat(findRecipe).isNull();
    }

    @Test
    void noAuthorizerDeleteRecipeTest() {
        // given
        Recipe testRecipe = createRecipeWithWriter("김치찌개", "맛있는 김치찌개를 끓여봅시다!", writer);
        recipeRepository.save(testRecipe);

        // then
        assertThatThrownBy(()->recipeService.deleteRecipe(testRecipe.getId(), noAuthorizer))
                .isInstanceOf(UnauthorizedAccessException.class);
    }


    private Recipe createRecipeWithWriter(String title, String instruction, Member authorizer) {
        return Recipe.builder()
                .title(title)
                .instruction(instruction)
                .member(authorizer)
                .build();
    }
}
