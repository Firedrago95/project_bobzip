package project.bobzip.entity.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.service.RecipeLikeService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeLikeController {

    private final RecipeLikeService recipeLikeService;

    @GetMapping("/recipe/searchRecipeCount/{recipeId}")
    public ResponseEntity<?> searchRecipeCount(@PathVariable("recipeId") Long recipeId) {
        try {
            Long likeCount = recipeLikeService.countLike(recipeId);
            return ResponseEntity.ok().body(likeCount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/recipe/like/{recipeId}")
    public ResponseEntity<?> like(
            @PathVariable("recipeId") Long recipeId,
            @SessionAttribute(LoginConst.LOGIN) Member loginMember) {
        try {
            recipeLikeService.addLike(loginMember, recipeId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/recipe/cancelLike/{recipeId}")
    public ResponseEntity<?> cancelLike(
            @PathVariable("recipeId") Long recipeId,
            @SessionAttribute(LoginConst.LOGIN) Member loginMember) {
        try {
            recipeLikeService.cancelLike(loginMember, recipeId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
