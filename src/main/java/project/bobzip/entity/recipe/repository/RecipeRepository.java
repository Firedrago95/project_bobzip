package project.bobzip.entity.recipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.bobzip.entity.recipe.entity.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findAll(Pageable pageable);

    @Query("select count(r) from Recipe r where r.id IN :recipeIds")
    int countAllById(@Param("recipeIds") List<Long> recipeIds);
}
