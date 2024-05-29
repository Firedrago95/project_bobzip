package project.bobzip.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bobzip.recipe.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
