package project.bobzip.entity.reply.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.exception.UnauthorizedAccessException;
import project.bobzip.entity.recipe.repository.RecipeRepository;
import project.bobzip.entity.reply.dto.request.ReplyAddForm;
import project.bobzip.entity.reply.dto.response.ReplyDto;
import project.bobzip.entity.reply.entity.Reply;
import project.bobzip.entity.reply.repository.ReplyRepository;

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
    public Page<ReplyDto> addReply(ReplyAddForm replyAddForm, Member loginMember, int pageSize) {
        addReplyEntity(replyAddForm, loginMember);

        Long recipeId = replyAddForm.getRecipeId();
        Long replyCounts = countAllReplies(recipeId);
        int lastPage = ((int) Math.ceil((double) replyCounts / pageSize)) - 1;

        Page<Reply> replies = replyRepository.findByRecipeId(recipeId, PageRequest.of(lastPage, pageSize));
        return ReplyDto.toDtoReplyPage(replies);
    }

    protected void addReplyEntity(ReplyAddForm replyAddForm, Member loginMember) {
        Recipe findRecipe = recipeRepository.findById(replyAddForm.getRecipeId())
                .orElseThrow(() -> new IllegalArgumentException("레시피를 찾을 수 없습니다."));

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
    public void editReply(Long commentId, String comment, Member loginMember) throws UnauthorizedAccessException {
        Reply reply = replyRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (loginMember == null || !reply.getMember().equals(loginMember)) {
            throw new UnauthorizedAccessException("댓글 수정");
        }

        reply.update(comment);
    }

    public Long countAllReplies(Long recipeId) {
        return replyRepository.countByRecipeId(recipeId);
    }

    @Transactional
    public Page<ReplyDto> deleteReply(Long commentId, Member loginMember, int pageSize) throws UnauthorizedAccessException {
        Reply reply = replyRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (loginMember == null || !reply.getMember().equals(loginMember)) {
            throw new UnauthorizedAccessException("댓글 삭제");
        }

        replyRepository.delete(reply);

        Long recipeId = reply.getRecipe().getId();
        Long replyCounts = replyRepository.countByRecipeId(recipeId);
        int lastPage = ((int) Math.ceil((double) replyCounts / pageSize)) - 1;

        Page<Reply> replies = replyRepository.findByRecipeId(recipeId, PageRequest.of(lastPage, pageSize));
        return ReplyDto.toDtoReplyPage(replies);
    }
}
