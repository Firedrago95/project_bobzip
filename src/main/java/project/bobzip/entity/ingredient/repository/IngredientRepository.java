package project.bobzip.entity.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bobzip.entity.ingredient.entity.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    boolean existsByName(String name);

    Optional<Ingredient> findByName(String name);
}
