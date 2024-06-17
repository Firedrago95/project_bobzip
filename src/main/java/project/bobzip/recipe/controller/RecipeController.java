package project.bobzip.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.bobzip.ingredient.entity.Ingredient;
import project.bobzip.ingredient.service.IngredientService;
import project.bobzip.member.dto.LoginConst;
import project.bobzip.member.entity.Member;
import project.bobzip.recipe.dto.RecipeAddForm;
import project.bobzip.recipe.entity.Recipe;
import project.bobzip.recipe.entity.Unit;
import project.bobzip.recipe.service.RecipeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @ModelAttribute("units")
    public List<Unit> units() {
        Unit[] values = Unit.values();
        return new ArrayList<>(Arrays.asList(values));
    }

    @GetMapping("/add")
    public String recipeForm(@ModelAttribute("recipeAddForm") RecipeAddForm recipeAddForm) {
        return "/recipe/recipeForm";
    }

    @PostMapping("/add")
    public String addRecipe(@Validated @ModelAttribute RecipeAddForm recipeAddForm,
                            BindingResult bindingResult,
                            @SessionAttribute(name = LoginConst.LOGIN) Member loginMember) throws IOException {
        // 입력 정보 오류 있다면 입력폼으로
        if (bindingResult.hasErrors()) {
            return "/recipe/recipeForm";
        }
        // 재료 등록 후 반환
        List<Ingredient> ingredients = ingredientService.addIngredients(recipeAddForm.getIngredientNames());
        // 반환받은 재료와 로그인 멤버, 입력정보를 바탕으로 레시피 등록
        recipeService.addRecipe(recipeAddForm, loginMember, ingredients);
        return "redirect:/";
    }

    @GetMapping("/all")
    public String readAllRecipes(@PageableDefault(size = 2) Pageable pageable,
                                 Model model) {
        Page<Recipe> page = recipeService.findAllRecipes(pageable);
        List<Recipe> allRecipes = page.getContent();

        model.addAttribute("recipes", allRecipes);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", page.getNumber() + 1);
        return "/recipe/allRecipe";
    }

    @GetMapping("/{id}")
    public String viewRecipe(@PathVariable("id") Long id,
                             @SessionAttribute(value = LoginConst.LOGIN, required = false) Member member, Model model) {
        Recipe recipe = recipeService.findRecipe(id);
        // 게시글 작성자와 현재 로그인 유저가 같으면 isWriter 전달, 수정, 삭제 버튼 표시
        if (member.equals(recipe.getMember())) {
            log.info("작성자 확인");
            model.addAttribute("isWriter", true);
        }
        model.addAttribute("recipe", recipe);
        return "/recipe/recipeView";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable("id") Long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/recipe/all";
    }

}
