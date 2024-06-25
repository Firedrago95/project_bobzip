package project.bobzip.entity.reply.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReplyAddForm {

    private Long recipeId;
    private String comment;

    public ReplyAddForm(Long recipeId, String comment) {
        this.recipeId = recipeId;
        this.comment = comment;
    }
}
