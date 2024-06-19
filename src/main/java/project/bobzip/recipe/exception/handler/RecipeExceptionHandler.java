package project.bobzip.recipe.exception.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import project.bobzip.recipe.exception.NoSearchResultException;

@Slf4j
@ControllerAdvice
public class RecipeExceptionHandler {

    @ExceptionHandler(NoSearchResultException.class)
    public ModelAndView handleNoSearchResultException(NoSearchResultException e) {
        log.info("NoSearchResultException 예외처리기 실행");
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMessage", e.getMessage());
        mv.setViewName("/recipe/error/noSearchResult");
        return mv;
    }
}
