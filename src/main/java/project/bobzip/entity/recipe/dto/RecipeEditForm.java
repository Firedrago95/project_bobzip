package project.bobzip.entity.recipe.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.validation.NotEmptyList;
import project.bobzip.global.entity.UploadFile;

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

    private UploadFile recipeThumbnail;

    private boolean changedRecipeThumbnail;

    @NotEmptyList(message = "재료를 입력해주세요")
    private List<String> ingredientNames = new ArrayList<>();

    @NotEmptyList(message = "수량을 입력해주세요")
    private List<Integer> quantities = new ArrayList<>();

    @NotEmptyList(message = "단위를 입력해주세요")
    private List<String> units = new ArrayList<>();

    @NotNull @Valid
    private List<RecipeEditForm.Step> steps = new ArrayList<>();

    private List<Integer> changedStepThumbnail = new ArrayList<>();

    @Getter
    @Setter
    public static class Step {

        private Integer stepNumber;

        @NotEmpty(message = "조리법을 입력해주세요")
        private String instruction;

        private MultipartFile thumbnail;  // 이미지 업로드는 선택 사항

        private UploadFile stepThumbnail;
    }

    public static RecipeEditForm createEditForm(Recipe recipe) {
        RecipeEditForm recipeEditForm = new RecipeEditForm();
        recipeEditForm.setTitle(recipe.getTitle());
        recipeEditForm.setInstruction(recipe.getInstruction());
        recipeEditForm.setRecipeThumbnail(recipe.getThumbnail());

        recipe.getRecipeIngredients().forEach(ingredient -> {
            recipeEditForm.getIngredientNames().add(ingredient.getIngredient().getName());
            recipeEditForm.getQuantities().add(ingredient.getQuantity());
            recipeEditForm.getUnits().add(ingredient.getUnit());
        });

        recipe.getRecipeSteps().forEach(recipeStep -> {
            Step step = new Step();
            step.setStepNumber(recipeStep.getStepNumber());
            step.setInstruction(recipeStep.getInstruction());
            step.setStepThumbnail(recipeStep.getThumbnail());
            recipeEditForm.getSteps().add(step);
        });

        return recipeEditForm;
    }
}
