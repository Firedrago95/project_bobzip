package project.bobzip.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.bobzip.member.dto.LoginForm;
import project.bobzip.member.entity.Member;
import project.bobzip.member.service.MemberService;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("memberForm") LoginForm loginForm) {
        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("memberForm") LoginForm loginForm,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }
        Member loginMember = memberService.login(loginForm.getUserId(), loginForm.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }
        return null;
    }

}
