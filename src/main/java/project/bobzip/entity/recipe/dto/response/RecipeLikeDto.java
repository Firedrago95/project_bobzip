package project.bobzip.entity.recipe.dto.response;

import lombok.Data;

@Data
public class RecipeLikeDto {

    private Long likeCounts;

    private boolean checkedLike;

    public RecipeLikeDto(Long likeCounts, boolean checkedLike) {
        this.likeCounts = likeCounts;
        this.checkedLike = checkedLike;
    }
}
