package project.bobzip;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.bobzip.member.dto.LoginConst;
import project.bobzip.member.entity.Member;
import project.bobzip.recipe.dto.RecipeConst;
import project.bobzip.recipe.entity.Ingredient;
import project.bobzip.recipe.entity.Recipe;
import project.bobzip.recipe.service.RecipeService;

import java.util.List;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(@SessionAttribute(name = LoginConst.LOGIN, required = false) Member loginMember,
                       Model model) {
        return "home";
    }
}
