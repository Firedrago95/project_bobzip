package project.bobzip.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.bobzip.member.entity.Member;
import project.bobzip.member.service.MemberService;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("member") Member member) {
        return "member/loginForm";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "member/addForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("member") Member member,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addForm";
        }

        return "redirect:/";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("member") Member member,
                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addForm";
        }
        memberService.add(member);
        return "redirect:/";
    }
}
