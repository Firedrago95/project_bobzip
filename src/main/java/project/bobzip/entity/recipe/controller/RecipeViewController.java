package project.bobzip.entity.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.dto.response.RecipeSearchDTO;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.service.RecipeSearchService;
import project.bobzip.entity.recipe.service.RecipeService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeViewController {

    private final RecipeService recipeService;
    private final RecipeSearchService recipeSearchService;

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

        if (member != null && member.equals(recipe.getMember())) {
            model.addAttribute("isWriter", true);
        }
        model.addAttribute("recipe", recipe);
        return "/recipe/recipeView";
    }

    @GetMapping("/search")
    public String searchRecipe(@RequestParam(name = "q", defaultValue = "")String q, Model model,
                               @PageableDefault(size = 2) Pageable pageable) {
        Page<Recipe> page = recipeSearchService.searchRecipe(q, pageable);
        List<Recipe> allRecipes = page.getContent();

        model.addAttribute("recipes", allRecipes);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", page.getNumber() + 1);
        return "/recipe/allRecipe";
    }

    @ResponseBody
    @PostMapping("/searchByIngredients")
    public ResponseEntity<Page<RecipeSearchDTO>> searchByIngredient(@RequestBody List<String> ingredientNames,
                                                                  @PageableDefault(size = 5) Pageable pageable) {
        Page<RecipeSearchDTO> searchRecipes = recipeSearchService.searchByIngredient(ingredientNames, pageable);
        return ResponseEntity.ok().body(searchRecipes);
    }
}
