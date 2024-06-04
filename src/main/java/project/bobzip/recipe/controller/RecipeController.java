package project.bobzip.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.member.entity.Member;
import project.bobzip.recipe.dto.RecipeForm;
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
    public String recipeForm(@ModelAttribute("recipeForm")RecipeForm recipeForm) {
        return "/recipe/recipeForm";
    }

    @PostMapping("/add")
    public String addRecipe(@Validated @ModelAttribute RecipeForm recipeForm,
                            BindingResult bindingResult,
                            @SessionAttribute(name = "loginMember", required = true) Member loginMember) throws IOException {
        // 필드별 미입력 검증
        if (bindingResult.hasErrors()) {
            return "/recipe/recipeForm";
        }

        recipeService.addRecipe(recipeForm, loginMember);
        return "redirect:/";
    }
}
