package plango.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import plango.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
