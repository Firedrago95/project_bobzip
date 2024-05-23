package project.bobzip.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    private String userId;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    @NotNull
    @Size(max = 15)
    private String username;
}
