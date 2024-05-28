package project.bobzip.recipe.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.recipe.entity.Unit;

import java.util.List;

@Getter
@Setter
public class RecipeForm {

    @NotEmpty(message = "요리명을 입력해주세요")
    private String title;
    @NotEmpty(message = "요리 설명을 입력해주세요")
    private String instruction;
    private MultipartFile thumbnail;

    @NotEmpty(message = "재료명을 입력해주세요")
    private List<String> ingredientName;
    @NotEmpty(message = "재료 수량을 입력해주세요")
    private List<Integer> quantity;
    @NotEmpty(message = "단위를 입력해주세요")
    private List<String> unit;

    @NotEmpty(message = "조리 단계를 입력해주세요")
    private List<String> stepInstruction;
    private List<MultipartFile> stepThumbnail;
}
