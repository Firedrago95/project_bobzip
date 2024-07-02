package project.bobzip.entity.reply.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.recipe.entity.Recipe;
import project.bobzip.global.entity.BaseEntity;

@Entity
@Getter
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(length = 400)
    private String comment;

    public Reply() {}

    @Builder
    public Reply(Member member, Recipe recipe, String comment) {
        this.member = member;
        this.recipe = recipe;
        this.comment = comment;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
