package project.bobzip.entity.recipe.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.bobzip.entity.recipe.entity.Recipe;

import java.util.List;

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

    public List<Tuple> findAllSortedRecipes(List<String> ingredientNames) {
        return queryFactory
                .select(recipe.id, recipe.title, recipeIngredient.ingredient.name.count())
                .from(recipe)
                .join(recipe.recipeIngredients, recipeIngredient)
                .join(recipeIngredient.ingredient, ingredient)
                .where(ingredient.name.in(ingredientNames))
                .groupBy(recipe.id, recipe.title)
                .orderBy(recipeIngredient.ingredient.name.count().desc())
                .fetch();
    }

    public List<Tuple> findIngredientTuples(List<Long> recipeIds) {
        return queryFactory
                .select(recipe.id, ingredient.name)
                .from(recipe)
                .join(recipe.recipeIngredients, recipeIngredient)
                .join(recipeIngredient.ingredient, ingredient)
                .where(recipe.id.in(recipeIds))
                .fetch();
    }

    public Long countRecipesByIngredients(List<String> ingredientNames) {
        return queryFactory
                .select(recipe.countDistinct())
                .from(recipe)
                .join(recipe.recipeIngredients, recipeIngredient)
                .join(recipeIngredient.ingredient, ingredient)
                .where(ingredient.name.in(ingredientNames))
                .fetchOne();
    }
}
