package project.bobzip.recipe.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.member.entity.Member;
import project.bobzip.member.repository.MemberRepository;
import project.bobzip.member.service.MemberService;
import project.bobzip.recipe.entity.Recipe;
import project.bobzip.recipe.entity.RecipeStep;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecipeRepositoryTest {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void recipeSaveTest() {
        // given
        Member member = memberRepository.findByUserId("a").get();
        Recipe kimchiStew = Recipe.builder()
                .title("김치찌개")
                .instruction("맛있는 김치찌개를 끓여봅시다!")
                .build();

        // when
        recipeRepository.save(kimchiStew);

        // then
        Recipe findRecipe = recipeRepository.findById(kimchiStew.getId()).get();
        assertThat(findRecipe).isEqualTo(kimchiStew);
        assertThat(findRecipe.getTitle()).isEqualTo("김치찌개");
        assertThat(findRecipe.getInstruction()).isEqualTo("맛있는 김치찌개를 끓여봅시다!");
    }
}