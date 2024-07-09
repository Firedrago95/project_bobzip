package project.bobzip.entity.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.bobzip.entity.ingredient.entity.Ingredient;
import project.bobzip.entity.ingredient.service.IngredientService;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.dto.request.RecipeEditForm;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.exception.UnauthorizedAccessException;
import project.bobzip.entity.recipe.service.RecipeService;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeEditController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @GetMapping("/edit/{id}")
    public String editRecipeForm(@PathVariable("id") Long id,
                             @SessionAttribute(LoginConst.LOGIN) Member loginMember,
                             Model model) {
        Recipe recipe = recipeService.findRecipe(id);
        if (!(loginMember.equals(recipe.getMember()))) {
            throw new UnauthorizedAccessException("레시피 수정");
        }
        RecipeEditForm recipeEditForm = RecipeEditForm.createEditForm(recipe);
        model.addAttribute("recipeEditForm", recipeEditForm);
        return "/recipe/recipeEdit";
    }

    @PostMapping("/edit/{id}")
    public String editRecipe(@PathVariable("id") Long id,
                             @SessionAttribute(LoginConst.LOGIN) Member loginMember,
                             @ModelAttribute("recipeEditForm") RecipeEditForm recipeEditForm) throws IOException {
        log.info("changedThumbnail = {}", recipeEditForm.isChangedRecipeThumbnail());
        Recipe recipe = recipeService.findRecipe(id);
        if (!(loginMember.equals(recipe.getMember()))) {
            throw new UnauthorizedAccessException("레시피 수정");
        }
        List<Ingredient> ingredients = ingredientService.addIngredients(recipeEditForm.getIngredientNames());
        recipeService.updateRecipe(id, recipeEditForm, ingredients);
        return "redirect:/recipe/all";
    }
}
