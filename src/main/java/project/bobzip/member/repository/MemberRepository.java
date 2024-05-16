package project.bobzip.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bobzip.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String loginId);
}
