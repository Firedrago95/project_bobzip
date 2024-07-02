package project.bobzip.entity.reply.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    private final ReplyService replyService;

    @GetMapping("/all/{recipeId}")
    public Page<ReplyDto> getRepliesByRecipeId(@PathVariable("recipeId") Long recipeId,
                                               @PageableDefault(size = 10, page = 0, sort = "createdTime") Pageable pageable) {
        Page<Reply> replyPage = replyService.findAll(recipeId, pageable);
        Page<ReplyDto> reply = ReplyDto.toDtoReplyPage(replyPage);
        return reply;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReply(
            @RequestBody ReplyAddForm replyAddForm, HttpSession session) {
        Member loginMember = (Member) session.getAttribute(LoginConst.LOGIN);
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("댓글을 작성하려면, 로그인 해주세요");
        }
        Reply reply = replyService.addReply(replyAddForm, loginMember);
        ReplyDto replyDto = ReplyDto.toDtoReply(reply);
        return ResponseEntity.ok(replyDto);
    }

    @PostMapping("/edit/{commentId}")
    public ResponseEntity<?> editReply(
            @PathVariable("commentId") Long commentId,
            @ModelAttribute("comment") String comment,
            HttpSession session) {
        log.info("comment = {}", comment);
        Reply reply = replyService.findById(commentId);
        Member loginMember = (Member) session.getAttribute(LoginConst.LOGIN);
        if (loginMember == null || !(reply.getMember().equals(loginMember))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("댓글 작성자만 수정 할 수 있습니다.");
        }

        replyService.updateReply(reply, comment);
        return ResponseEntity.ok("댓글 수정 완료");
    }
}
