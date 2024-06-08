package project.bobzip.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @ModelAttribute("units")
    public List<Unit> units() {
        Unit[] values = Unit.values();
        return new ArrayList<>(Arrays.asList(values));
    }

    @GetMapping("/add")
    public String recipeForm(@ModelAttribute("recipeForm") RecipeAddForm recipeAddForm,
                             @SessionAttribute(name = LoginConst.LOGIN, required = false) Member loginMember) {
        // 로그인 검증
        if (loginMember == null) {return "redirect:/members/login";}
        return "/recipe/recipeForm";
    }

    @PostMapping("/add")
    public String addRecipe(@Validated @ModelAttribute RecipeAddForm recipeAddForm,
                            BindingResult bindingResult,
                            @SessionAttribute(name = LoginConst.LOGIN, required = false) Member loginMember) throws IOException {
        // 로그인 검증
        if (loginMember == null) {return "redirect:/members/login";}
        // 입력 정보 오류 있다면 입력폼으로
        if (bindingResult.hasErrors()) {
            return "/recipe/recipeForm";
        }

        recipeService.addRecipe(recipeAddForm, loginMember);
        return "redirect:/";
    }

    @GetMapping("/all")
    public String readAllRecipes(@PageableDefault(size = 1, sort = "title") Pageable pageable,
                                 Model model) {
        List<Recipe> allRecipes = recipeService.readAllRecipes(pageable);
        model.addAttribute("recipes", allRecipes);
        return "/recipe/allRecipe";
    }
}
