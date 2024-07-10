package project.bobzip.entity.recipe.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.recipe.dto.response.RecipeSearchDTO;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.exception.NoSearchResultException;
import project.bobzip.entity.recipe.repository.RecipeSearchRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static project.bobzip.entity.ingredient.entity.QIngredient.ingredient;
import static project.bobzip.entity.recipe.entity.QRecipe.recipe;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeSearchService {

    private final RecipeSearchRepository recipeSearchRepository;


    public Page<Recipe> searchRecipe(String q, Pageable pageable) {
        Page<Recipe> recipes = recipeSearchRepository.searchRecipes(q, pageable);
        List<Recipe> content = recipes.getContent();
        if (content.isEmpty()) {
            throw new NoSearchResultException(q);
        }
        return recipes;
    }

    public Page<RecipeSearchDTO> searchByIngredient(List<String>ingredientNames, Pageable pageable) {
        List<Tuple> sortedRecipes = recipeSearchRepository.findAllSortedRecipes(ingredientNames);
        List<Long> paginatedRecipeIds = findPaginatedRecipeIds(sortedRecipes, pageable);
        
        List<Tuple> ingredientTuples = recipeSearchRepository.findIngredientTuples(paginatedRecipeIds);

        Map<Long, RecipeSearchDTO> recipeMap = new LinkedHashMap<>();
        createRecipeSearchDto(sortedRecipes, recipeMap);
        addIngredientsToRecipeSearchDto(ingredientNames, ingredientTuples, recipeMap);

        List<RecipeSearchDTO> contents = new ArrayList<>(recipeMap.values());
        Long count = recipeSearchRepository.countRecipesByIngredients(ingredientNames);
        return new PageImpl<>(contents, pageable, count);
    }

    private List<Long> findPaginatedRecipeIds(List<Tuple> sortedRecipes, Pageable pageable) {
        List<Long> allRecipeIds = sortedRecipes.stream()
                .map(tuple -> tuple.get(recipe.id))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sortedRecipes.size());
        List<Long> paginatedRecipeIds = allRecipeIds.subList(start, end);
        return paginatedRecipeIds;
    }

    private void createRecipeSearchDto(List<Tuple> sortedRecipes, Map<Long, RecipeSearchDTO> recipeMap) {
        for (Tuple tuple : sortedRecipes) {
            Long recipeId = tuple.get(recipe.id);
            String recipeTitle = tuple.get(recipe.title);
            recipeMap.put(recipeId, new RecipeSearchDTO(recipeId, recipeTitle));
        }
    }

    private void addIngredientsToRecipeSearchDto(List<String> ingredientNames, List<Tuple> ingredientTuples, Map<Long, RecipeSearchDTO> recipeMap) {
        for (Tuple tuple : ingredientTuples) {
            Long recipeId = tuple.get(recipe.id);
            String ingredientName = tuple.get(ingredient.name);
            RecipeSearchDTO dto = recipeMap.get(recipeId);
            if (dto != null) {
                if (ingredientNames.contains(ingredientName)) {
                    dto.addAvailableIngredients(ingredientName);
                } else {
                    dto.addRequiredIngredients(ingredientName);
                }
            }
        }
    }
}
