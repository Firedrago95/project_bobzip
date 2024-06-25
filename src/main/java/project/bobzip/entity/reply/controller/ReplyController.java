package project.bobzip.entity.reply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.bobzip.entity.reply.dto.response.ReplyDto;
import project.bobzip.entity.reply.entity.Reply;
import project.bobzip.entity.reply.service.ReplyService;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/all")
    public Page<ReplyDto> getRepliesByRecipeId(@PathVariable("recipeId") Long recipeId,
            @PageableDefault(size = 10, page = 0, sort = "createdTime") Pageable pageable) {
        Page<Reply> replyPage = replyService.findAll(recipeId, pageable);
        Page<ReplyDto> reply = ReplyDto.toDtoReplyPage(replyPage);
        return reply;
    }
}
