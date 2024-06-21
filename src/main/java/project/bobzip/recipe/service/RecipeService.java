package project.bobzip.recipe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.global.entity.UploadFile;
import project.bobzip.global.util.fileupload.FileStore;
import project.bobzip.ingredient.entity.Ingredient;
import project.bobzip.member.entity.Member;
import project.bobzip.recipe.dto.RecipeAddForm;
import project.bobzip.recipe.entity.*;
import project.bobzip.recipe.exception.NoSearchResultException;
import project.bobzip.recipe.exception.UnauthorizedAccessException;
import project.bobzip.recipe.repository.RecipeRepository;
import project.bobzip.recipe.repository.RecipeSearchRepository;

import java.io.IOException;
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
}
