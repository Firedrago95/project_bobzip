package project.bobzip.entity.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.service.RecipeService;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.reply.entity.Reply;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeViewController {

    private final RecipeService recipeService;

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
        if (member != null && member.equals(recipe.getMember())) {
            log.info("작성자 확인");
            model.addAttribute("isWriter", true);
        }
        model.addAttribute("recipe", recipe);
        return "/recipe/recipeView";
    }

    @GetMapping("/search")
    public String searchRecipe(@RequestParam(name = "q", defaultValue = "")String q, Model model,
                               @PageableDefault(size = 2) Pageable pageable) {
        Page<Recipe> page = recipeService.searchRecipe(q, pageable);
        List<Recipe> allRecipes = page.getContent();

        model.addAttribute("recipes", allRecipes);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", page.getNumber() + 1);
        return "/recipe/allRecipe";
    }
}
