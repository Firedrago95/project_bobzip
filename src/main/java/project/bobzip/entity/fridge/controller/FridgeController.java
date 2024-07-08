package project.bobzip.entity.fridge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.bobzip.entity.fridge.dto.response.FridgeIngredientDto;
import project.bobzip.entity.fridge.entity.FridgeIngredient;
import project.bobzip.entity.fridge.service.FridgeService;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeService fridgeService;

    @GetMapping
    public String fridgePage() {
        return "/fridge/myFridge";
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<FridgeIngredientDto> loadFridgeIngredient(@SessionAttribute(LoginConst.LOGIN) Member loginMember) {
        List<FridgeIngredient> ingredients = fridgeService.findAll(loginMember);
        log.info("isEmpty = {}", ingredients.isEmpty());
        return ResponseEntity.ok()
                .body(new FridgeIngredientDto(ingredients));
    }
}
