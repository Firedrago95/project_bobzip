package project.bobzip.entity.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bobzip.entity.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String loginId);

    boolean existsByUserId(String userId);

    boolean existsByUsername(String username);
}
