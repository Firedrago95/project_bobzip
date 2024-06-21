package project.bobzip.entity.recipe.exception;

public class NoSearchResultException extends RuntimeException{

    public NoSearchResultException(String query) {
        super(query +"에 대한 검색결과가 없습니다.");
    }
}
