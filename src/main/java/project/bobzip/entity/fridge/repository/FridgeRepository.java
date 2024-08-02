package project.bobzip.entity.fridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.bobzip.entity.fridge.entity.FridgeIngredient;
import project.bobzip.entity.member.entity.Member;

import java.util.List;

@Repository
public interface FridgeRepository extends JpaRepository<FridgeIngredient, Long> {

    @Query("select fi from FridgeIngredient fi JOIN FETCH fi.ingredient where fi.member = :member")
    List<FridgeIngredient> findAllIngredients(@Param("member") Member member);

    @Modifying
    @Query("delete from FridgeIngredient fi where fi.member = :member")
    void deleteAllByMember(@Param("member")Member loginMember);
}
