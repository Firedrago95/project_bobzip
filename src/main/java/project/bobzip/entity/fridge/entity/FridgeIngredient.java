package project.bobzip.entity.fridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import project.bobzip.entity.ingredient.entity.Ingredient;
import project.bobzip.entity.member.entity.Member;

@Entity
@Getter
public class FridgeIngredient {

    @Id
    @GeneratedValue
    @Column(name = "fridge_ingredient_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected FridgeIngredient() {}

    public FridgeIngredient(Ingredient ingredient, Member member) {
        this.ingredient = ingredient;
        this.member = member;
    }
}
