package project.bobzip.entity.reply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.reply.dto.request.ReplyAddForm;
import project.bobzip.entity.reply.dto.response.ReplyDto;
import project.bobzip.entity.reply.entity.Reply;
import project.bobzip.entity.reply.service.ReplyService;

@Slf4j
@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    public static final int PAGE_SIZE = 10;
    private final ReplyService replyService;

    @GetMapping("/all/{recipeId}")
    public Page<ReplyDto> getRepliesByRecipeId(@PathVariable("recipeId") Long recipeId,
                                               @PageableDefault(size = 10, page = 0, sort = "createdTime") Pageable pageable) {
        Page<Reply> replyPage = replyService.findAll(recipeId, pageable);
        Page<ReplyDto> reply = ReplyDto.toDtoReplyPage(replyPage);
        return reply;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReply(@RequestBody ReplyAddForm replyAddForm,
                                      @SessionAttribute(LoginConst.LOGIN) Member loginMember) {
        Page<ReplyDto> replyDtoPage = replyService.addReply(replyAddForm, loginMember, PAGE_SIZE);
        return ResponseEntity.ok(replyDtoPage);
    }

    @PostMapping("/edit/{commentId}")
    public ResponseEntity<String> editReply(
            @PathVariable("commentId") Long commentId, @ModelAttribute("comment") String comment,
            @SessionAttribute(LoginConst.LOGIN) Member loginMember) {
        replyService.editReply(commentId, comment, loginMember);
        return ResponseEntity.ok("댓글 수정 완료");
    }

    @PostMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteReply(@PathVariable("commentId") Long commentId,
                                         @SessionAttribute(LoginConst.LOGIN) Member loginMember) {
        Page<ReplyDto> replyDtoPage = replyService.deleteReply(commentId, loginMember, PAGE_SIZE);
        return ResponseEntity.ok(replyDtoPage);
    }
}
