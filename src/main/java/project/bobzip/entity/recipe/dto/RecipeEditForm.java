package project.bobzip.entity.recipe.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecipeEditForm {

    @NotEmpty(message = "요리명을 입력해주세요")
    private String title;

    @NotEmpty(message = "요리 설명을 입력해주세요")
    private String instruction;

    private MultipartFile thumbnail;

    private boolean changedRecipeThumbnail;

    private List<@NotEmpty(message = "재료명을 입력해주세요")String> ingredientNames = new ArrayList<>();

    private List<@NotNull(message = "재료수량을 입력해주세요")Integer> quantities = new ArrayList<>();

    private List<@NotNull(message = "단위를 입력해주세요")String> units = new ArrayList<>();

    private List<@NotEmpty(message = "조리법을 입력해주세요")String> stepInstructions = new ArrayList<>();

    private List<MultipartFile> stepThumbnails = new ArrayList<>();

    private List<Integer> changedStepThumbnail = new ArrayList<>();
}
