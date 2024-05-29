package project.bobzip.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bobzip.recipe.entity.RecipeStep;

public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
}
