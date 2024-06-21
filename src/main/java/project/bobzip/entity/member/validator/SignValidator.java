package project.bobzip.entity.member.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.bobzip.entity.member.dto.SignForm;
import project.bobzip.entity.member.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class SignValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignForm signForm = (SignForm) target;
        if (memberRepository.existsByUsername(signForm.getUserId())) {
            errors.rejectValue("userId", "duplicate.userId", "이미 사용중인 아이디입니다.");
        }

        if (memberRepository.existsByUsername(signForm.getUsername())) {
            errors.rejectValue("username", "duplicate.username", "이미 사용중인 닉네임입니다.");
        }
    }
}
