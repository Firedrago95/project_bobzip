package project.bobzip.member.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.member.entity.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    private Member testMember;

    @BeforeEach
    void initData() {
        testMember = new Member("userA", "abcd", "1234");
        memberRepository.save(testMember);
    }

    @Test
    void saveTest() {
        // then
        Member findMember = memberRepository.findById(testMember.getId()).get();
        assertThat(findMember).isEqualTo(testMember);
    }

    @Test
    void findByUserIdTest() {
        // when
        Member loginMember = memberRepository.findByUserId("userA").get();

        // then
        assertThat(loginMember).isEqualTo(testMember);
    }

    @Test
    void deleteMemberTest() {
        // when
        memberRepository.delete(testMember);

        // then
        Optional<Member> deletedMember = memberRepository.findByUserId("abcd");
        assertThat(deletedMember).isEmpty();
    }
}