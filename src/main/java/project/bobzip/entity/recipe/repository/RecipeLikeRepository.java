package project.bobzip.entity.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.entity.recipe.entity.RecipeLike;

import java.util.Optional;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {

    @Query("select count(rl) from RecipeLike rl where rl.recipe = :recipe")
    Long countLikes(@Param("recipe") Recipe recipe);

    Optional<RecipeLike> findByMemberAndRecipe(Member loginMember, Recipe recipe);

    @Modifying
    @Query("delete from RecipeLike rl where rl.member = :member and rl.recipe = :recipe")
    void deleteRecipeLike(@Param("member") Member loginMember,
                          @Param("recipe") Recipe recipe);
}
