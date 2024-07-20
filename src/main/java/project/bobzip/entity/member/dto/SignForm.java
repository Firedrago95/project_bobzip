package project.bobzip.entity.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignForm {

    @NotEmpty(message = "아이디를 입력해주세요")
    private String userId;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;
    @NotEmpty(message = "닉네임을 입력해주세요")
    private String username;
}
