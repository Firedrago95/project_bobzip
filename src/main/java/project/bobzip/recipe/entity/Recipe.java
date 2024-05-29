package project.bobzip.recipe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import project.bobzip.global.entity.UploadFile;
import project.bobzip.member.entity.Member;

@Entity
@Getter
public class Recipe {

    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Size(max = 50)
    private String title;

    @Column(length = 100)
    private String instruction;

    @Embedded
    private UploadFile thumbnail;

    public Recipe(Member member, String title, String instruction, UploadFile thumbnail) {
        this.member = member;
        this.title = title;
        this.instruction = instruction;
        this.thumbnail = thumbnail;
    }
}
