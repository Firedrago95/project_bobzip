package project.bobzip.entity.fridge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import project.bobzip.entity.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Fridge {

    @Id
    @GeneratedValue
    @Column(name = "fridge_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "fridgeId", cascade = CascadeType.ALL)
    private List<FridgeIngredient> fridgeIngredients = new ArrayList<>();

    /** 생성 메서드 **/
    protected Fridge() {}

    public Fridge(Member member, List<FridgeIngredient> fridgeIngredients) {
        this.member = member;

        if (fridgeIngredients != null) {
            for (FridgeIngredient fridgeIngredient : fridgeIngredients) {
                fridgeIngredients.add(fridgeIngredient);
                addFridgeIngredient(fridgeIngredient);
            }
        }
    }

    /** 연관관계 메서드 **/
    private void addFridgeIngredient(FridgeIngredient fridgeIngredient) {
        fridgeIngredients.add(fridgeIngredient);
        fridgeIngredient.add(this);
    }
}
