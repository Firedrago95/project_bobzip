package project.bobzip.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignForm {

    @NotNull
    private String userId;
    @NotNull
    private String password;
    @NotNull
    private String username;
}
