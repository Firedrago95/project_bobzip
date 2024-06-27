package project.bobzip.entity.recipe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.entity.recipe.validation.NotEmptyFile;
import project.bobzip.entity.recipe.validation.NotEmptyList;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecipeAddForm {

    @NotEmpty(message = "요리명을 입력해주세요")
    private String title;

    @NotEmpty(message = "요리 설명을 입력해주세요")
    private String instruction;

    @NotEmptyFile(message = "요리 이미지를 첨부해주세요")
    private MultipartFile thumbnail;

    @NotEmptyList(message = "재료를 입력해주세요")
    private List<String> ingredientNames = new ArrayList<>();

    @NotEmptyList(message = "수량을 입력해주세요")
    private List<Integer> quantities = new ArrayList<>();

    @NotEmptyList(message = "단위를 입력해주세요")
    private List<String> units = new ArrayList<>();

    @NotNull @Valid
    private List<Step> steps = new ArrayList<>();

    @Getter
    @Setter
    public static class Step {

        private Integer stepNumber;

        @NotEmpty(message = "조리법을 입력해주세요")
        private String instruction;

        private MultipartFile thumbnail;  // 이미지 업로드는 선택 사항
    }
}
