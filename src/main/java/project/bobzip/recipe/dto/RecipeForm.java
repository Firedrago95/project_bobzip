package project.bobzip.recipe.dto;

import jdk.jfr.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.recipe.entity.Unit;

import java.util.List;

@Getter
@Setter
public class RecipeForm {

    private String title;
    private String instruction;
    private MultipartFile thumbnail;

    private List<String> ingredientName;
    private List<Integer> quantity;
    private List<String> unit;

    private List<String> stepInstruction;
    private List<MultipartFile> stepThumbnail;
}
