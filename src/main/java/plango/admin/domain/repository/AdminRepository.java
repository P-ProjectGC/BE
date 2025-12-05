package plango.admin.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.admin.domain.entity.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByLoginId(String loginId);
}
