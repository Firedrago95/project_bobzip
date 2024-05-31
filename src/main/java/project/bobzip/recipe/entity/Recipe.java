package project.bobzip.recipe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import project.bobzip.global.entity.UploadFile;
import project.bobzip.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Recipe {

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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeStep> recipeSteps = new ArrayList<>();

    //==생성 메서드==//
    public static Recipe createRecipe(List<RecipeIngredient> recipeIngredients,
                                List<RecipeStep> recipeSteps, String instruction,
                                Member member, String title, UploadFile uploadFile) {
        Recipe recipe = new Recipe();
        recipe.member = member;
        recipe.title = title;
        recipe.instruction = instruction;
        recipe.thumbnail = uploadFile;

        for (RecipeStep recipeStep : recipeSteps) {
            recipe.addRecipeStep(recipeStep);
        }

        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            recipe.addRecipeIngredient(recipeIngredient);
        }

        return recipe;
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
