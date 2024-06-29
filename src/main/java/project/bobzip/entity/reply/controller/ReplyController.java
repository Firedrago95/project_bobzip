package project.bobzip.entity.reply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.reply.dto.request.ReplyAddForm;
import project.bobzip.entity.reply.dto.response.ReplyDto;
import project.bobzip.entity.reply.entity.Reply;
import project.bobzip.entity.reply.service.ReplyService;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/all")
    @ResponseBody
    public Page<ReplyDto> getRepliesByRecipeId(@PathVariable("recipeId") Long recipeId,
                                               @PageableDefault(size = 10, page = 0, sort = "createdTime") Pageable pageable) {
        Page<Reply> replyPage = replyService.findAll(recipeId, pageable);
        Page<ReplyDto> reply = ReplyDto.toDtoReplyPage(replyPage);
        return reply;
    }

    @PostMapping("/add")
    public String addReply(
            @ModelAttribute("replyAddForm")ReplyAddForm replyAddForm,
            @SessionAttribute(LoginConst.LOGIN) Member loginMember) {
        replyService.addReply(replyAddForm, loginMember);
        return "redirect:/recipe/" + replyAddForm.getRecipeId();
    }

}
