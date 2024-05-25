package project.bobzip.recipe.dto;

import jdk.jfr.Category;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.recipe.entity.IngredientCategory;
import project.bobzip.recipe.entity.Unit;

import java.util.List;

public class RecipeForm {

    @Getter
    public static class Create {

        private String title;
        private String instruction;
        private MultipartFile thumbnail;

        private List<String> ingredientName;
        private List<String> category;
        private List<String> unit;
    }
}
