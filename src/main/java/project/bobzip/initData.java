package project.bobzip;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.bobzip.member.dto.LoginForm;
import project.bobzip.member.entity.Member;
import project.bobzip.member.service.MemberService;
import project.bobzip.recipe.service.RecipeService;

@Component
@RequiredArgsConstructor
public class initData {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        Member member = new Member("a", "123", "a");
        memberService.add(member);
    }
}
