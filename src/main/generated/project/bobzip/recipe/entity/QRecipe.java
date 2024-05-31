package project.bobzip.recipe.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipe is a Querydsl query type for Recipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipe extends EntityPathBase<Recipe> {

    private static final long serialVersionUID = 881934278L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipe recipe = new QRecipe("recipe");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath instruction = createString("instruction");

    public final project.bobzip.member.entity.QMember member;

    public final ListPath<RecipeIngredient, QRecipeIngredient> recipeIngredients = this.<RecipeIngredient, QRecipeIngredient>createList("recipeIngredients", RecipeIngredient.class, QRecipeIngredient.class, PathInits.DIRECT2);

    public final ListPath<RecipeStep, QRecipeStep> recipeSteps = this.<RecipeStep, QRecipeStep>createList("recipeSteps", RecipeStep.class, QRecipeStep.class, PathInits.DIRECT2);

    public final project.bobzip.global.entity.QUploadFile thumbnail;

    public final StringPath title = createString("title");

    public QRecipe(String variable) {
        this(Recipe.class, forVariable(variable), INITS);
    }

    public QRecipe(Path<? extends Recipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipe(PathMetadata metadata, PathInits inits) {
        this(Recipe.class, metadata, inits);
    }

    public QRecipe(Class<? extends Recipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new project.bobzip.member.entity.QMember(forProperty("member")) : null;
        this.thumbnail = inits.isInitialized("thumbnail") ? new project.bobzip.global.entity.QUploadFile(forProperty("thumbnail")) : null;
    }

}

