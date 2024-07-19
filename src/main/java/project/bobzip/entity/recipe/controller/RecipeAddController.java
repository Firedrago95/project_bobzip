package project.bobzip.entity.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.bobzip.entity.ingredient.entity.Ingredient;
import project.bobzip.entity.ingredient.service.IngredientService;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.dto.request.RecipeAddForm;
import project.bobzip.entity.recipe.service.RecipeService;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeAddController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;


    @GetMapping("/add")
    public String recipeForm(@ModelAttribute("recipeAddForm") RecipeAddForm recipeAddForm) {
        return "recipe/recipeForm";
    }

    @PostMapping("/add")
    public String addRecipe(@Validated @ModelAttribute RecipeAddForm recipeAddForm,
                            BindingResult bindingResult,
                            @SessionAttribute(name = LoginConst.LOGIN) Member loginMember) throws IOException {
        // 입력 정보 오류 있다면 입력폼으로
        if (bindingResult.hasErrors()) {
            return "recipe/recipeForm";
        }
        // 재료 등록 후 반환
        List<Ingredient> ingredients = ingredientService.addIngredients(recipeAddForm.getIngredientNames());
        // 반환받은 재료와 로그인 멤버, 입력정보를 바탕으로 레시피 등록
        recipeService.addRecipe(recipeAddForm, loginMember, ingredients);
        return "redirect:/";
    }
}
