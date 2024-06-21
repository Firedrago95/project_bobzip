package project.bobzip.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.bobzip.entity.member.dto.LoginConst;
import project.bobzip.entity.member.entity.Member;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(@SessionAttribute(name = LoginConst.LOGIN, required = false) Member loginMember,
                       Model model) {
        return "home";
    }
}
