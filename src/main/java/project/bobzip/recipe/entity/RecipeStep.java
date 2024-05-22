package project.bobzip.recipe.entity;

import jakarta.persistence.*;
import lombok.Getter;

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
    private String thumbnail;

    @Column(name = "instruction")
    private String instruction;
}
