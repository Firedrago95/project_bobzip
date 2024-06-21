package project.bobzip.entity.recipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.bobzip.entity.recipe.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findAll(Pageable pageable);
}
