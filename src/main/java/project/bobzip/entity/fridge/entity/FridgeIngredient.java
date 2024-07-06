package project.bobzip.entity.fridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import project.bobzip.entity.ingredient.entity.Ingredient;

@Entity
@Getter
public class FridgeIngredient {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredientId;

    /** 연관관계 메서드 **/
    public void add(Fridge fridge) {
        this.fridge = fridge;
    }
}
