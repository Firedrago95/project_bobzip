package project.bobzip.member.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.member.entity.Member;
import project.bobzip.entity.member.repository.MemberRepository;
import project.bobzip.entity.member.service.MemberService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;
    
    @Test
    void addTest() {
        // given
        Member member = new Member("abc", "123", "yong");

        // when
        memberService.add(member);
        
        // then
        Member findMember = memberRepository.findByUserId("abc").get();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void loginTest() {
        // given
        Member registeredMember = new Member("register", "123", "member");
        memberService.add(registeredMember);
        Member unRegisteredMember = new Member("unRegister", "456", "notMember");

        // when
        Member loginMember1 = memberService.login(registeredMember.getUserId(), registeredMember.getPassword());
        Member loginMember2 = memberService.login(unRegisteredMember.getUserId(), unRegisteredMember.getPassword());

        // then
        assertThat(loginMember1).isEqualTo(registeredMember);
        assertThat(loginMember2).isNull();
    }

    @Test
    void deleteTest() {
        // given
        Member member = new Member("abc", "123", "yong");
        memberService.add(member);

        // when
        memberService.delete(member);

        // then
        Optional<Member> findMember = memberRepository.findByUserId("abc");
        assertThat(findMember).isEmpty();
    }
}