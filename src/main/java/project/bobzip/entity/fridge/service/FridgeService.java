package project.bobzip.entity.fridge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.bobzip.entity.fridge.entity.FridgeIngredient;
import project.bobzip.entity.fridge.repository.FridgeRepository;
import project.bobzip.entity.ingredient.entity.Ingredient;
import project.bobzip.entity.member.entity.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FridgeService {

    private final FridgeRepository fridgeRepository;

    public List<FridgeIngredient> findAll(Member loginMember) {
        return fridgeRepository.findAllIngredients(loginMember);
    }
}
