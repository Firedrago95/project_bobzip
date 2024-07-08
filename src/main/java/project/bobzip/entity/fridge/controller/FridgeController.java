package project.bobzip.entity.fridge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.bobzip.entity.fridge.dto.response.FridgeIngredientDto;
import project.bobzip.entity.fridge.entity.FridgeIngredient;
import project.bobzip.entity.fridge.service.FridgeService;
import project.bobzip.entity.ingredient.entity.Ingredient;
import project.bobzip.entity.ingredient.service.IngredientService;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeService fridgeService;
    private final IngredientService ingredientService;

    @GetMapping
    public String fridgePage() {
        return "/fridge/myFridge";
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<FridgeIngredientDto> loadFridgeIngredient(@SessionAttribute(LoginConst.LOGIN) Member loginMember) {
        List<FridgeIngredient> ingredients = fridgeService.findAll(loginMember);
        return ResponseEntity.ok()
                .body(new FridgeIngredientDto(ingredients));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFridgeIngredient(
            @SessionAttribute(LoginConst.LOGIN) Member loginMember,
            @RequestBody List<String> ingredientNames) {
        log.info("ingredientNames = {}", ingredientNames);
        List<Ingredient> ingredients = ingredientService.addIngredients(ingredientNames);
        fridgeService.addFridgeIngredient(ingredients, loginMember);
        return ResponseEntity.ok().build();
    }
}
