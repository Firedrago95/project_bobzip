package project.bobzip.recipe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.global.entity.UploadFile;
import project.bobzip.global.util.fileupload.FileStore;
import project.bobzip.member.entity.Member;
import project.bobzip.recipe.dto.RecipeForm;
import project.bobzip.recipe.entity.*;
import project.bobzip.recipe.repository.IngredientRepository;
import project.bobzip.recipe.repository.RecipeIngredientRepository;
import project.bobzip.recipe.repository.RecipeRepository;
import project.bobzip.recipe.repository.RecipeStepRepository;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final FileStore fileStore;

    @Transactional
    public void addRecipe(RecipeForm recipeForm, Member member) throws IOException {
        // 파일 저장
        MultipartFile thumbnail = recipeForm.getThumbnail();
        List<MultipartFile> stepThumbnails = recipeForm.getStepThumbnail();
        UploadFile recipeThumbnailUrl = fileStore.addThumbnail(thumbnail);
        List<UploadFile> stepThumbnailUrls = fileStore.addThumbnail(stepThumbnails);

        // 재료 생성
        List<Ingredient> ingredients = Ingredient.createIngredients(recipeForm.getIngredientName());
        ingredientRepository.saveAll(ingredients);

        // 레시피 재료 생성
        List<RecipeIngredient> recipeIngredients = RecipeIngredient.createRecipeIngredient(
                ingredients,
                recipeForm.getQuantity(),
                recipeForm.getUnit());

        // 레시피 단계 생성
        List<RecipeStep> recipeSteps = RecipeStep.createRecipeSteps(
                stepThumbnailUrls,
                recipeForm.getStepInstruction());

        // 레시피 생성
        Recipe recipe = Recipe.createRecipe(recipeIngredients, recipeSteps, recipeForm.getInstruction(),
                member, recipeForm.getTitle(), recipeThumbnailUrl);
        recipeRepository.save(recipe);
    }

}
