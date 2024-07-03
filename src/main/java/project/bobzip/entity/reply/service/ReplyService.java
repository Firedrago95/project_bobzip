package project.bobzip.entity.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.repository.RecipeRepository;
import project.bobzip.entity.reply.dto.request.ReplyAddForm;
import project.bobzip.entity.reply.entity.Reply;
import project.bobzip.entity.reply.repository.ReplyRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final RecipeRepository recipeRepository;

    public Page<Reply> findAll(Long id, Pageable pageable) {
        return replyRepository.findByRecipeId(id, pageable);
    }

    @Transactional
    public void addReply(ReplyAddForm replyAddForm, Member loginMember) {
        Recipe findRecipe = recipeRepository.findById(replyAddForm.getRecipeId())
                .orElseThrow(()-> new IllegalArgumentException("레시피를 찾을 수 없습니다."));

        Reply reply = Reply.builder()
                .recipe(findRecipe)
                .member(loginMember)
                .comment(replyAddForm.getComment())
                .build();

        replyRepository.save(reply);
    }

    public Reply findById(Long commentId) {
        return replyRepository.findById(commentId).orElse(null);
    }

    @Transactional
    public void updateReply(Reply reply, String comment) {
        reply.update(comment);
    }

    public Long countAllReplies(Long recipeId) {
        return replyRepository.countByRecipeId(recipeId);
    }

    @Transactional
    public void deleteReply(Reply reply) {
        replyRepository.delete(reply);
    }
}
