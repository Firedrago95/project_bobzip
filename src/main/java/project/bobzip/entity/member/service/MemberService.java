package project.bobzip.entity.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bobzip.entity.member.repository.MemberRepository;
import project.bobzip.entity.member.entity.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void add(Member member) {
        memberRepository.save(member);
    }

    public Member login(String userId, String password) {
        return memberRepository.findByUserId(userId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public void delete(Member member) {
        memberRepository.delete(member);
    }
}
