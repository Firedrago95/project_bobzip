package project.bobzip.entity.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import project.bobzip.entity.member.entity.Member;

@Entity
@Getter
public class RecipeLike {

    @Id
    @GeneratedValue
    @Column(name = "recipe_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    protected RecipeLike() {}

    public RecipeLike (Member member, Recipe recipe) {
        this.member = member;
        this.recipe = recipe;
    }
}
