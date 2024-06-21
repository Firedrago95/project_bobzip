package project.bobzip.entity.recipe.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.bobzip.entity.recipe.entity.Recipe;

import java.util.List;

import static project.bobzip.entity.recipe.entity.QRecipe.recipe;

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
}
