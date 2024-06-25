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
    private String username;
    private String comment;
    private LocalDateTime createdTime;

    public static Page<ReplyDto> toDtoReplyPage(Page<Reply> replyPage) {
        return replyPage.map(r -> ReplyDto.builder()
                .id(r.getId())
                .username(r.getMember().getUsername())
                .comment(r.getComment())
                .createdTime(r.getCreatedTime())
                .build());
    }
}
