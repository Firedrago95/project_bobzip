package project.bobzip.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import project.bobzip.global.entity.UploadFile;

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

    public RecipeStep(Recipe recipe, int stepNumber, UploadFile thumbnail, String instruction) {
        this.recipe = recipe;
        this.stepNumber = stepNumber;
        this.thumbnail = thumbnail;
        this.instruction = instruction;
    }
}
