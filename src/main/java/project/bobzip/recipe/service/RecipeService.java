package project.bobzip.recipe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.global.entity.UploadFile;
import project.bobzip.global.util.fileupload.FileStore;
import project.bobzip.member.entity.Member;
import project.bobzip.recipe.dto.RecipeAddForm;
import project.bobzip.recipe.entity.*;
import project.bobzip.recipe.repository.IngredientRepository;
import project.bobzip.recipe.repository.RecipeRepository;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final FileStore fileStore;

    @Transactional
    public void addRecipe(RecipeAddForm recipeAddForm, Member member) throws IOException {
        // 파일 저장
        MultipartFile thumbnail = recipeAddForm.getThumbnail();
        List<MultipartFile> stepThumbnails = recipeAddForm.getStepThumbnails();
        UploadFile recipeThumbnailUrl = fileStore.addThumbnail(thumbnail);
        List<UploadFile> stepThumbnailUrls = fileStore.addThumbnail(stepThumbnails);

        // 재료 생성
        List<Ingredient> ingredients = Ingredient.createIngredients(recipeAddForm.getIngredientNames());
        ingredientRepository.saveAll(ingredients);

        // 레시피 재료 생성
        List<RecipeIngredient> recipeIngredients = RecipeIngredient.createRecipeIngredient(
                ingredients,
                recipeAddForm.getQuantities(),
                recipeAddForm.getUnits());

        // 레시피 단계 생성
        List<RecipeStep> recipeSteps = RecipeStep.createRecipeSteps(
                stepThumbnailUrls,
                recipeAddForm.getStepInstructions());

        // 레시피 생성
        Recipe recipe = Recipe.createRecipe(recipeIngredients, recipeSteps, recipeAddForm.getInstruction(),
                member, recipeAddForm.getTitle(), recipeThumbnailUrl);
        recipeRepository.save(recipe);
    }

    public List<Recipe> readAllRecipes(Pageable pageable) {
        return recipeRepository.findPagingRecipes(pageable).getContent();
    }

}
