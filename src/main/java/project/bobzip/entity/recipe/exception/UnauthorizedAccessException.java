package project.bobzip.entity.recipe.exception;

public class UnauthorizedAccessException extends RuntimeException{

    public UnauthorizedAccessException(String message) {
        super(message + "할 권한이 없습니다.");
    }
}
