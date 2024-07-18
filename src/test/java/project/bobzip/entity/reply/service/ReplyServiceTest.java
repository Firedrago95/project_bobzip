package project.bobzip.entity.reply.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.member.repository.MemberRepository;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.repository.RecipeRepository;
import project.bobzip.entity.reply.dto.request.ReplyAddForm;
import project.bobzip.entity.reply.entity.Reply;
import project.bobzip.entity.reply.repository.ReplyRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RecipeRepository recipeRepository;


    @Test
    void findAllTest() {
        // given
        Member testMember = createTestMember();
        Recipe testRecipe = createTestRecipe();
        createTestReplies(testRecipe, testMember);
        PageRequest pageable = PageRequest.of(0, 1);

        // when
        Page<Reply> pagingReply = replyService.findAll(testRecipe.getId(), pageable);

        // then
        List<Reply> content = pagingReply.getContent();
        assertThat(pagingReply.getTotalPages()).isEqualTo(2);
        assertThat(content.size()).isEqualTo(1);

    }

    @Test
    void addReplyEntityTest() {
        // given
        Recipe testRecipe = createTestRecipe();
        Member testMember = createTestMember();
        ReplyAddForm replyAddForm = new ReplyAddForm(testRecipe.getId(), "조리법 정말 좋아요");

        // when
        replyService.addReplyEntity(replyAddForm, testMember);

        // then
        Page<Reply> result = replyRepository.findByRecipeId(testRecipe.getId(), PageRequest.of(0, 10));
        List<Reply> content = result.getContent();
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getComment()).isEqualTo("조리법 정말 좋아요");
    }

    @Test
    @DisplayName("댓글 추가 예외발생")
    void addReplyExceptionTest() {
        // given
        Long wrongRecipeId = 22345L;
        Member testMember = createTestMember();
        ReplyAddForm replyAddForm = new ReplyAddForm(wrongRecipeId, "존재하지 않는 레시피에 댓글 등록 예외발생");

        // then
        Assertions.assertThatThrownBy(() ->replyService.addReplyEntity(replyAddForm, testMember))
                .isInstanceOf(IllegalArgumentException.class);
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
