package project.bobzip.entity.reply.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import project.bobzip.entity.reply.entity.Reply;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ReplyDto {

    private Long id;
    private Long recipeId;
    private String username;
    private String comment;
    private LocalDateTime createdTime;

    public static Page<ReplyDto> toDtoReplyPage(Page<Reply> replyPage) {
        return replyPage.map(r -> ReplyDto.builder()
                .id(r.getId())
                .recipeId(r.getRecipe().getId())
                .username(r.getMember().getUsername())
                .comment(r.getComment())
                .createdTime(r.getCreatedTime())
                .build());
    }

    public static ReplyDto toDtoReply(Reply reply) {
        return ReplyDto.builder()
                .id(reply.getId())
                .recipeId(reply.getRecipe().getId())
                .username(reply.getMember().getUsername())
                .comment(reply.getComment())
                .createdTime(reply.getCreatedTime())
                .build();
    }
}
