package project.bobzip.entity.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import project.bobzip.global.entity.UploadFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class RecipeStep {

    @Id
    @GeneratedValue
    @Column(name = "recipe_step_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(name = "stepNumber")
    private int stepNumber;

    @Column(name = "thumbnail")
    @Embedded
    private UploadFile thumbnail;

    @Column(name = "instruction")
    private String instruction;


    //==생성 메서드==//
    public RecipeStep() {}

    public static RecipeStep createRecipeStep(UploadFile image, String instruction, int stepNumber) {
        RecipeStep recipeStep = new RecipeStep();
        recipeStep.stepNumber = stepNumber;
        recipeStep.instruction = instruction;
        recipeStep.thumbnail = image;
        return recipeStep;
    }

    //==연관 메서드==//
    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
