package project.bobzip.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.bobzip.member.entity.Member;
import project.bobzip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void add(Member member) {
        memberRepository.save(member);
    }
}
