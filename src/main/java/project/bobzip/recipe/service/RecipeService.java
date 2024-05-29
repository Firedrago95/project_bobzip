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
        UploadFile uploadFile = fileStore.addThumbnail(thumbnail);

        // 레시피 생성
        Recipe recipe = new Recipe(member, recipeForm.getTitle(), recipeForm.getInstruction(), uploadFile);
        recipeRepository.save(recipe);
        
        // 레시피 단계 생성
        List<String> stepInstruction = recipeForm.getStepInstruction();
        List<MultipartFile> stepThumbnail = recipeForm.getStepThumbnail();
        List<UploadFile> uploadStepThumbnail = fileStore.addThumbnail(stepThumbnail);
        for (int i = 0; i < stepInstruction.size(); i++) {
            recipeStepRepository.save(
                    new RecipeStep(
                            recipe, i + 1,
                            uploadStepThumbnail.get(i)
                            ,stepInstruction.get(i)
                    )
            );
        }

        // 재료 및 레시피 재료 생성
        List<String> ingredientName = recipeForm.getIngredientName();
        List<Unit> unit = recipeForm.getUnit();
        List<Integer> quantity = recipeForm.getQuantity();

        for (int i = 0; i < ingredientName.size(); i++) {
            Ingredient ingredient = new Ingredient(ingredientName.get(i));
            ingredientRepository.save(ingredient);

            RecipeIngredient recipeIngredient =
                    new RecipeIngredient(recipe, ingredient, quantity.get(i), unit.get(i));
            recipeIngredientRepository.save(recipeIngredient);
        }
    }
}
