package project.bobzip.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.bobzip.recipe.entity.Recipe;
import project.bobzip.recipe.entity.RecipeStep;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
