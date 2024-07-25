package project.bobzip.entity.fridge.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import project.bobzip.entity.fridge.entity.FridgeIngredient;
import project.bobzip.entity.ingredient.entity.Ingredient;
import project.bobzip.entity.ingredient.repository.IngredientRepository;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.member.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Profile("prod")
class FridgeRepositoryTest {

    @Autowired
    FridgeRepository fridgeRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    void findAllIngredientsTest() {
        // given
        Member member = new Member("test", "123", "test");
        memberRepository.save(member);

        Ingredient kimchi = new Ingredient("김치");
        Ingredient pork = new Ingredient("돼지고기");
        ingredientRepository.save(kimchi);
        ingredientRepository.save(pork);

        FridgeIngredient fridgeIngredient1 = new FridgeIngredient(kimchi, member);
        FridgeIngredient fridgeIngredient2 = new FridgeIngredient(pork, member);
        fridgeRepository.save(fridgeIngredient1);
        fridgeRepository.save(fridgeIngredient2);

        // when
        List<FridgeIngredient> fridgeIngredients = fridgeRepository.findAllIngredients(member);

        // then
        assertThat(fridgeIngredients).hasSize(2);
        assertThat(fridgeIngredients).extracting("ingredient").contains(kimchi, pork);
    }
}