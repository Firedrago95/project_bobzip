package project.bobzip.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import project.bobzip.recipe.dto.RecipeForm;
import project.bobzip.recipe.entity.IngredientCategory;
import project.bobzip.recipe.entity.Unit;
import project.bobzip.recipe.service.RecipeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeFormController {

    @ModelAttribute("categories")
    public List<IngredientCategory> categories() {
        IngredientCategory[] values = IngredientCategory.values();
        return new ArrayList<>(Arrays.asList(values));
    }

    @ModelAttribute("units")
    public List<Unit> units() {
        Unit[] values = Unit.values();
        return new ArrayList<>(Arrays.asList(values));
    }

    @GetMapping("/add")
    public String recipeForm(@ModelAttribute("recipeForm")RecipeForm.Create addRecipe) {
        return "/recipe/recipeForm";
    }
}
