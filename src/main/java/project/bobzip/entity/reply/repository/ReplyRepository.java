package project.bobzip.entity.reply.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.bobzip.entity.reply.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r JOIN FETCH r.member where r.recipe.id = :recipeId")
    Page<Reply> findByRecipeId(@Param("recipeId") Long recipeId, Pageable pageable);

    @Query("select count(r) from Reply r where r.recipe.id = :recipeId")
    Long countByRecipeId(@Param("recipeId")Long recipeId);
}
