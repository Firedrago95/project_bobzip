package project.bobzip.recipe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipeStep is a Querydsl query type for RecipeStep
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipeStep extends EntityPathBase<RecipeStep> {

    private static final long serialVersionUID = 115828658L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipeStep recipeStep = new QRecipeStep("recipeStep");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath instruction = createString("instruction");

    public final QRecipe recipe;

    public final NumberPath<Integer> stepNumber = createNumber("stepNumber", Integer.class);

    public final project.bobzip.global.entity.QUploadFile thumbnail;

    public QRecipeStep(String variable) {
        this(RecipeStep.class, forVariable(variable), INITS);
    }

    public QRecipeStep(Path<? extends RecipeStep> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipeStep(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipeStep(PathMetadata metadata, PathInits inits) {
        this(RecipeStep.class, metadata, inits);
    }

    public QRecipeStep(Class<? extends RecipeStep> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recipe = inits.isInitialized("recipe") ? new QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
        this.thumbnail = inits.isInitialized("thumbnail") ? new project.bobzip.global.entity.QUploadFile(forProperty("thumbnail")) : null;
    }

}
