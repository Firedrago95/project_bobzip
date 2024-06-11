package project.bobzip.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.bobzip.member.dto.LoginConst;
import project.bobzip.member.dto.LoginForm;
import project.bobzip.member.entity.Member;
import project.bobzip.member.service.MemberService;

@Slf4j
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
    public String login(@Validated @ModelAttribute("memberForm") LoginForm loginForm, BindingResult bindingResult,
                        @RequestParam(name = "redirectURL", defaultValue = "/") String redirectURL,
                        HttpSession session) {
        log.info("redirectURL={}", redirectURL);
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        Member loginMember = memberService.login(loginForm.getUserId(), loginForm.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }

        session.setAttribute(LoginConst.LOGIN, loginMember);

        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginMember");
        return "redirect:/";
    }
}
