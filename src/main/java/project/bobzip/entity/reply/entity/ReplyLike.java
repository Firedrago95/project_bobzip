package project.bobzip.entity.reply.entity;

import jakarta.persistence.*;
import project.bobzip.entity.member.entity.Member;

@Entity
public class ReplyLike {

    @Id
    @GeneratedValue
    @Column(name = "reply_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;
}
