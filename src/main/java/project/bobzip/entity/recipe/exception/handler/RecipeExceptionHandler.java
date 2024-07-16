package project.bobzip.entity.recipe.exception.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import project.bobzip.entity.recipe.exception.NoSearchResultException;
import project.bobzip.global.exception.UnauthorizedAccessException;

@Slf4j
@ControllerAdvice("project.bobzip.entity.recipe")
public class RecipeExceptionHandler {

    @ExceptionHandler(NoSearchResultException.class)
    public ModelAndView handleNoSearchResultException(NoSearchResultException e) {
        log.info("NoSearchResultException 예외처리기 실행");
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMessage", e.getMessage());
        mv.setViewName("/recipe/error/noSearchResult");
        return mv;
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ModelAndView handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        log.info("UnauthorizedAccessException 예외처리기 실행");
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMessage", e.getMessage());
        mv.setViewName("/recipe/error/unAuthorizedAccess");
        return mv;
    }
}
