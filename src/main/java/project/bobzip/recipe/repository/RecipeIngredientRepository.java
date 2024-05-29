package project.bobzip.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bobzip.recipe.entity.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
}
