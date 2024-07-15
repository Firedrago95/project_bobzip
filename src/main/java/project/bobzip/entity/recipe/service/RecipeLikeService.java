package project.bobzip.entity.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.dto.response.RecipeLikeDto;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.entity.RecipeLike;
import project.bobzip.entity.recipe.repository.RecipeLikeRepository;
import project.bobzip.entity.recipe.repository.RecipeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeLikeService {

    private final RecipeRepository recipeRepository;
    private final RecipeLikeRepository recipeLikeRepository;

    @Transactional
    public void addLike(Member loginMember, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
        RecipeLike recipeLike = new RecipeLike(loginMember, recipe);
        recipeLikeRepository.save(recipeLike);
    }

    @Transactional
    public void cancelLike(Member loginMember, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
        recipeLikeRepository.deleteRecipeLike(loginMember, recipe);
    }

    public RecipeLikeDto searchLike(Member loginMember, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
        boolean isLiked = (loginMember != null) &&  recipeLikeRepository.checkLike(loginMember, recipe);
        Long likeCounts = recipeLikeRepository.countLikes(recipe);
        return new RecipeLikeDto(likeCounts, isLiked);
    }

}
