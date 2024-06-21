package project.bobzip.entity.recipe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import project.bobzip.global.entity.BaseEntity;
import project.bobzip.global.entity.UploadFile;
import project.bobzip.entity.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Size(max = 50)
    private String title;

    @Column(length = 100)
    private String instruction;

    @Embedded
    private UploadFile thumbnail;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeStep> recipeSteps = new ArrayList<>();

    //==생성 메서드==//
    public Recipe() {}
    @Builder
    public Recipe(List<RecipeIngredient> recipeIngredients,
                   List<RecipeStep> recipeSteps, String instruction,
                   Member member, String title, UploadFile uploadFile) {
        this.member = member;
        this.title = title;
        this.instruction = instruction;
        this.thumbnail = uploadFile;

        if (recipeSteps != null) {
            for (RecipeStep recipeStep : recipeSteps) {
                addRecipeStep(recipeStep);
            }
        }
        if (recipeIngredients != null) {
            for (RecipeIngredient recipeIngredient : recipeIngredients) {
                addRecipeIngredient(recipeIngredient);
            }
        }
    }

    //==연관관계 메서드==//
    public void addRecipeStep(RecipeStep recipeStep) {
        recipeSteps.add(recipeStep);
        recipeStep.addRecipe(this);
    }

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredients.add(recipeIngredient);
        recipeIngredient.addRecipe(this);
    }
}
