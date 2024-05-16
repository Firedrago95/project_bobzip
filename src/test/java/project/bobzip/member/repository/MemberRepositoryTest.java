package project.bobzip.member.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.member.entity.Member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveTest() {
        Member member = new Member();
        member.setUsername("userA");
        member.setUserId("abcd");
        member.setPassword("1234");
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findByUserIdTest() {
        Member member = new Member();
        member.setUsername("userA");
        member.setUserId("abcd");
        member.setPassword("1234");
        memberRepository.save(member);

        Member loginMember = memberRepository.findByUserId("abcd").get();
        assertThat(loginMember).isEqualTo(member);
    }
}