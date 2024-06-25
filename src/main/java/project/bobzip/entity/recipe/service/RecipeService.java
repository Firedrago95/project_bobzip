package project.bobzip.entity.recipe.service;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.entity.recipe.dto.RecipeEditForm;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.entity.RecipeIngredient;
import project.bobzip.entity.recipe.entity.RecipeStep;
import project.bobzip.global.entity.UploadFile;
import project.bobzip.global.util.fileupload.FileStore;
import project.bobzip.entity.ingredient.entity.Ingredient;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.dto.RecipeAddForm;
import project.bobzip.entity.recipe.exception.NoSearchResultException;
import project.bobzip.entity.recipe.exception.UnauthorizedAccessException;
import project.bobzip.entity.recipe.repository.RecipeRepository;
import project.bobzip.entity.recipe.repository.RecipeSearchRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        UploadFile recipeThumbnailUrl = fileStore.addThumbnail(recipeAddForm.getThumbnail());
        List<UploadFile> stepThumbnailUrls = fileStore.addStepThumbnails(recipeAddForm.getStepThumbnails());

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

    public Page<Recipe> searchRecipe(String q, Pageable pageable) {
        Page<Recipe> recipes = recipeSearchRepository.searchRecipes(q, pageable);
        List<Recipe> content = recipes.getContent();
        if (content.isEmpty()) {
            throw new NoSearchResultException(q);
        }
        return recipes;
    }

    @Transactional
    public void updateRecipe(Long id, RecipeEditForm recipeEditForm, List<Ingredient> ingredients) throws IOException {
        Recipe recipe = recipeRepository.findById(id).get();

        updateRecipeBasicInfo(recipeEditForm, recipe);
        updateRecipeIngredients(recipeEditForm, ingredients, recipe);
        updateRecipeSteps(recipeEditForm, recipe);
    }

    private void updateRecipeSteps(RecipeEditForm recipeEditForm, Recipe recipe) throws IOException {
        List<String> stepInstructions = recipeEditForm.getStepInstructions();
        List<Integer> changedStepThumbnail = recipeEditForm.getChangedStepThumbnail();
        List<MultipartFile> stepThumbnails = recipeEditForm.getStepThumbnails();
        List<UploadFile> thumbnails = new ArrayList<>(recipe.getRecipeSteps().stream()
                .map(RecipeStep::getThumbnail)
                .toList());

        for (int i = 0; i < changedStepThumbnail.size(); i++) {
            int indexToChange = changedStepThumbnail.get(i) - 1;

            if (indexToChange < thumbnails.size()) {
                MultipartFile multipartFile = stepThumbnails.get(i);
                UploadFile newThumbnail = fileStore.updateStepThumbnail(thumbnails.get(i), multipartFile);
                thumbnails.set(indexToChange, newThumbnail);
            } else {
                thumbnails.add(fileStore.addThumbnail(stepThumbnails.get(i)));
            }
        }

        List<RecipeStep> recipeSteps = RecipeStep.createRecipeSteps(thumbnails, stepInstructions);
        recipe.updateRecipeSteps(recipeSteps);
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
