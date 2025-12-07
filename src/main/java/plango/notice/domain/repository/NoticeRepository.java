package plango.notice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.notice.domain.entity.Notice;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAllByOrderByIdDesc();

    List<Notice> findTop5ByOrderByCreatedAtDesc();
}
