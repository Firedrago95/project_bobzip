package project.bobzip.recipe.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class RecipeAddForm {

    @NotEmpty(message = "요리명을 입력해주세요")
    private String title;

    @NotEmpty(message = "요리 설명을 입력해주세요")
    private String instruction;

    @NotNull
    private MultipartFile thumbnail;

    private List<@NotEmpty(message = "재료명을 입력해주세요")String> ingredientNames;

    private List<@NotNull(message = "재료수량을 입력해주세요")Integer> quantities;

    private List<@NotNull(message = "단위를 입력해주세요")String> units;

    private List<@NotEmpty(message = "조리법을 입력해주세요")String> stepInstructions;

    private List<@NotNull(message = "조리법 이미지를 등록해주세요")MultipartFile> stepThumbnails;

}
