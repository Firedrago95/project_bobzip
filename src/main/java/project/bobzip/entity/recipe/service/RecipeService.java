package project.bobzip.entity.recipe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.ingredient.entity.Ingredient;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.dto.request.RecipeAddForm;
import project.bobzip.entity.recipe.dto.request.RecipeEditForm;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.entity.RecipeIngredient;
import project.bobzip.entity.recipe.entity.RecipeStep;
import project.bobzip.entity.recipe.exception.UnauthorizedAccessException;
import project.bobzip.entity.recipe.repository.RecipeRepository;
import project.bobzip.entity.recipe.repository.RecipeSearchRepository;
import project.bobzip.global.entity.UploadFile;
import project.bobzip.global.util.fileupload.FileStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final FileStore fileStore;
    private final RecipeSearchRepository recipeSearchRepository;

    @Transactional
    public void addRecipe(RecipeAddForm recipeAddForm, Member member, List<Ingredient> ingredients) throws IOException {
        // 파일 저장
        UploadFile recipeThumbnailUrl = fileStore.addThumbnail(recipeAddForm.getThumbnail(), true);

        // 레시피 재료 생성
        List<RecipeIngredient> recipeIngredients = RecipeIngredient.createRecipeIngredient(
                ingredients,
                recipeAddForm.getQuantities(),
                recipeAddForm.getUnits());

        // 레시피 단계 생성
        List<RecipeStep> recipeSteps = new ArrayList<>();

        List<RecipeAddForm.Step> steps = recipeAddForm.getSteps();
        for (RecipeAddForm.Step step : steps) {
            UploadFile uploadFile = fileStore.addThumbnail(step.getThumbnail(), false);
            RecipeStep recipeStep = RecipeStep.createRecipeStep(uploadFile, step.getInstruction(), step.getStepNumber());
            recipeSteps.add(recipeStep);
        }

        // 레시피 생성
        Recipe recipe = Recipe.builder()
                .recipeIngredients(recipeIngredients)
                .recipeSteps(recipeSteps)
                .instruction(recipeAddForm.getInstruction())
                .member(member)
                .title(recipeAddForm.getTitle())
                .uploadFile(recipeThumbnailUrl)
                .build();
        recipeRepository.save(recipe);
    }

    public Page<Recipe> findAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Recipe findRecipe(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteRecipe(Long id, Member loginMember) {
        Recipe recipe = recipeRepository.findById(id).get();
        if (!(loginMember.equals(recipe.getMember()))) {
            throw new UnauthorizedAccessException("레시피 삭제");
        }
        recipeRepository.delete(recipe);
    }

    @Transactional
    public void updateRecipe(Long id, RecipeEditForm recipeEditForm, List<Ingredient> ingredients) throws IOException {
        Recipe recipe = recipeRepository.findById(id).get();

        updateRecipeBasicInfo(recipeEditForm, recipe);
        updateRecipeIngredients(recipeEditForm, ingredients, recipe);
        updateRecipeSteps(recipeEditForm, recipe);
    }

    private void updateRecipeSteps(RecipeEditForm recipeEditForm, Recipe recipe) throws IOException {
        List<RecipeEditForm.Step> inputSteps = recipeEditForm.getSteps();
        List<Integer> changedStepThumbnails = recipeEditForm.getChangedStepThumbnail();
        List<RecipeStep> oldRecipeSteps = recipe.getRecipeSteps();

        List<RecipeStep> updatedRecipeSteps = new ArrayList<>();
        for (RecipeEditForm.Step inputStep : inputSteps) {
            Integer stepNumber = inputStep.getStepNumber();
            RecipeStep existingRecipeStep = oldRecipeSteps.stream()
                    .filter(rs -> stepNumber.equals(rs.getStepNumber()))
                    .findFirst()
                    .orElse(null);

            UploadFile uploadFile = getUploadFile(inputStep, existingRecipeStep, changedStepThumbnails);

            RecipeStep recipeStep = RecipeStep.createRecipeStep(uploadFile, inputStep.getInstruction(), stepNumber);
            updatedRecipeSteps.add(recipeStep);
        }
        recipe.updateRecipeSteps(updatedRecipeSteps);
    }

    private UploadFile getUploadFile(RecipeEditForm.Step step, RecipeStep existingRecipeStep, List<Integer> changedStepThumbnails) throws IOException {
        if (changedStepThumbnails.contains(step.getStepNumber())) {
            return (existingRecipeStep != null)
                    ? fileStore.updateStepThumbnail(existingRecipeStep.getThumbnail(), step.getThumbnail())
                    : fileStore.addThumbnail(step.getThumbnail(), false);
        } else {
            return (existingRecipeStep != null) ? existingRecipeStep.getThumbnail() : new UploadFile();
        }
    }

    private static void updateRecipeIngredients(RecipeEditForm recipeEditForm, List<Ingredient> ingredients, Recipe recipe) {
        List<RecipeIngredient> recipeIngredient = RecipeIngredient.createRecipeIngredient(
                ingredients,
                recipeEditForm.getQuantities(),
                recipeEditForm.getUnits());

        recipe.updateRecipeIngredient(recipeIngredient);
    }

    private void updateRecipeBasicInfo(RecipeEditForm recipeEditForm, Recipe recipe) throws IOException {
        recipe.updateTitle(recipeEditForm.getTitle());
        recipe.updateInstruction(recipeEditForm.getInstruction());
        if (recipeEditForm.isChangedRecipeThumbnail()) {
            UploadFile newRecipeThumbnail = fileStore.updateRecipeThumbnail(recipe.getThumbnail(), recipeEditForm.getThumbnail());
            recipe.updateRecipeThumbnail(newRecipeThumbnail);
        }
    }
}
