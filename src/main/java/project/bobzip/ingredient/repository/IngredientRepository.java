package project.bobzip.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bobzip.ingredient.entity.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    boolean existsByName(String name);

    Optional<Ingredient> findByName(String name);
}
