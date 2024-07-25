package project.bobzip.entity.reply.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.member.repository.MemberRepository;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.repository.RecipeRepository;
import project.bobzip.entity.reply.entity.Reply;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Profile("prod")
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void findByRecipeIdTest() {
        // given
        Recipe recipe = createTestRecipe();
        Member member = createTestMember();
        createTestReplies(recipe, member);
        PageRequest pageable = PageRequest.of(0, 1);

        // when
        Page<Reply> pagingReply = replyRepository.findByRecipeId(recipe.getId(), pageable);

        // then
        List<Reply> content = pagingReply.getContent();
        assertThat(pagingReply.getTotalPages()).isEqualTo(2);
        assertThat(content.size()).isEqualTo(1);
    }

    @Test
    void saveTest() {
        // given
        Member testMember = createTestMember();
        Recipe testRecipe = createTestRecipe();
        Reply testReply = createTestReply(testMember, testRecipe);

        // when
        replyRepository.save(testReply);

        // then
        Optional<Reply> result = replyRepository.findById(testReply.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getComment()).isEqualTo("정말 맛있는 요리입니다.");
    }

    private Reply createTestReply(Member testMember, Recipe testRecipe) {
        Reply testReply = Reply.builder()
                .recipe(testRecipe)
                .member(testMember)
                .comment("정말 맛있는 요리입니다.")
                .build();
        return testReply;
    }

    private void createTestReplies(Recipe recipe, Member member) {
        List<Reply> testReplies = Arrays.asList(
                Reply.builder()
                        .recipe(recipe)
                        .member(member)
                        .comment("정말 맛있는 요리입니다.")
                        .build(),
                Reply.builder()
                        .recipe(recipe)
                        .member(member)
                        .comment("조리법 대로 요리하니 간이 좀 맞지 않네요...")
                        .build()
        );
        replyRepository.saveAll(testReplies);
    }

    private Member createTestMember() {
        Member member = new Member("test", "123", "test");
        memberRepository.save(member);
        return member;
    }

    private Recipe createTestRecipe() {
        Recipe recipe = Recipe.builder()
                .title("된장찌개")
                .instruction("맛있는 된장찌개")
                .build();
        recipeRepository.save(recipe);
        return recipe;
    }
}