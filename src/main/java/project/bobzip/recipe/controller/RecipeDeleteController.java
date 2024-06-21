package project.bobzip.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.bobzip.member.dto.LoginConst;
import project.bobzip.member.entity.Member;
import project.bobzip.recipe.service.RecipeService;

@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeDeleteController {

    private final RecipeService recipeService;

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable("id") Long id,
                               @SessionAttribute(LoginConst.LOGIN) Member loginMember) {
        recipeService.deleteRecipe(id, loginMember);
        return "redirect:/recipe/all";
    }
}