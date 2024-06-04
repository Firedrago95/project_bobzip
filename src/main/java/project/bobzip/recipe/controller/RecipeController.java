package project.bobzip.recipe.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.member.dto.LoginConst;
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
    public String recipeForm(@ModelAttribute("recipeForm")RecipeForm recipeForm,
                             @SessionAttribute(name = LoginConst.LOGIN, required = false) Member loginMember) {
        // 로그인 검증
        if (loginMember == null) {return "redirect:/members/login";}
        return "/recipe/recipeForm";
    }

    @PostMapping("/add")
    public String addRecipe(@Validated @ModelAttribute RecipeForm recipeForm,
                            BindingResult bindingResult,
                            @SessionAttribute(name = LoginConst.LOGIN, required = false) Member loginMember) throws IOException {
        // 로그인 검증
        if (loginMember == null) {return "redirect:/members/login";}
        // 입력 정보 오류 있다면 입력폼으로
        if (bindingResult.hasErrors()) {
            return "/recipe/recipeForm";
        }

        recipeService.addRecipe(recipeForm, loginMember);
        return "redirect:/";
    }
}
