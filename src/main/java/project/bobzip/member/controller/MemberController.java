package project.bobzip.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import project.bobzip.member.dto.LoginConst;
import project.bobzip.member.dto.SignForm;
import project.bobzip.member.entity.Member;
import project.bobzip.member.service.MemberService;
import project.bobzip.member.validator.SignValidator;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SignValidator signValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(signValidator);
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") SignForm member) {
        return "members/addForm";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("member") SignForm signForm,
                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addForm";
        }

        Member member = new Member(signForm.getUserId(), signForm.getPassword(), signForm.getUsername());
        memberService.add(member);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String add(HttpSession session) {
        Member member = (Member) session.getAttribute(LoginConst.LOGIN);
        memberService.delete(member);
        session.removeAttribute(LoginConst.LOGIN);
        return "redirect:/";
    }
}
