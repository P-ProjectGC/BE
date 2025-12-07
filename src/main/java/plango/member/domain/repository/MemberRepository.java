package plango.member.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import plango.member.domain.entity.Member;
import plango.member.domain.entity.LoginType;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByLoginIdAndLoginType(String loginId, LoginType loginType);

    List<Member> findAllByNicknameContaining(String nickname);

    List<Member> findByLoginIdContainingOrEmailContainingOrNicknameContaining(
            String loginId,
            String email,
            String nickname
    );


    long count();

    long countByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
