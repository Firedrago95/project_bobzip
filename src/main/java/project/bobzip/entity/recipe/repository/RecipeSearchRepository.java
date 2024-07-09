package project.bobzip.entity.recipe.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.bobzip.entity.ingredient.entity.QIngredient;
import project.bobzip.entity.recipe.dto.response.RecipeSearchDTO;
import project.bobzip.entity.recipe.entity.QRecipe;
import project.bobzip.entity.recipe.entity.QRecipeIngredient;
import project.bobzip.entity.recipe.entity.Recipe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.list;
import static project.bobzip.entity.ingredient.entity.QIngredient.ingredient;
import static project.bobzip.entity.recipe.entity.QRecipe.recipe;
import static project.bobzip.entity.recipe.entity.QRecipeIngredient.recipeIngredient;

@Slf4j
@Repository
public class RecipeSearchRepository {

    @Autowired
    JPAQueryFactory queryFactory;

    public Page<Recipe> searchRecipes(String q, Pageable pageable) {
        List<Recipe> contents = queryFactory.select(recipe)
                .from(recipe)
                .where(recipe.title.like("%"+q+"%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long counts = queryFactory.select(recipe.count())
                .from(recipe)
                .where(recipe.title.like("%"+q+"%"))
                .fetchOne();

        return new PageImpl<>(contents, pageable, counts);
    }

    public Page<RecipeSearchDTO> searchByIngredient(List<String> ingredientNames, Pageable pageable) {
        List<Tuple> recipeTuples = queryFactory
                .select(recipe.id, recipe.title)
                .from(recipe)
                .join(recipe.recipeIngredients, recipeIngredient)
                .join(recipeIngredient.ingredient, ingredient)
                .where(ingredient.name.in(ingredientNames))
                .groupBy(recipe.id, recipe.title)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> recipeIds = recipeTuples.stream()
                .map(tuple -> tuple.get(recipe.id))
                .collect(Collectors.toList());

        List<Tuple> ingredientTuples = queryFactory
                .select(recipe.id, ingredient.name)
                .from(recipe)
                .join(recipe.recipeIngredients, recipeIngredient)
                .join(recipeIngredient.ingredient, ingredient)
                .where(recipe.id.in(recipeIds))
                .fetch();

        Map<Long, RecipeSearchDTO> recipeMap = new LinkedHashMap<>();

        for (Tuple tuple : recipeTuples) {
            Long recipeId = tuple.get(recipe.id);
            String recipeName = tuple.get(recipe.title);
            recipeMap.put(recipeId, new RecipeSearchDTO(recipeId, recipeName, new ArrayList<>()));
        }

        for (Tuple tuple : ingredientTuples) {
            Long recipeId = tuple.get(recipe.id);
            String ingredientName = tuple.get(ingredient.name);
            RecipeSearchDTO dto = recipeMap.get(recipeId);
            if (dto != null) {
                dto.getIngredients().add(ingredientName);
            }
        }

        List<RecipeSearchDTO> contents = new ArrayList<>(recipeMap.values());

        Long count = queryFactory
                .select(recipe.countDistinct())
                .from(recipe)
                .join(recipe.recipeIngredients, recipeIngredient)  // INNER JOIN
                .join(recipeIngredient.ingredient, ingredient)      // INNER JOIN
                .where(ingredient.name.in(ingredientNames))
                .fetchOne();

        return new PageImpl<>(contents, pageable, count);
    }
}
