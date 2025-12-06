package plango.report.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.report.domain.entity.InconvenienceReport;
import plango.report.domain.entity.InconvenienceReportStatus;
import java.util.List;

public interface InconvenienceReportRepository
        extends JpaRepository<InconvenienceReport, Long> {

    List<InconvenienceReport> findAllByOrderByStatusAscCreatedAtDesc();

    List<InconvenienceReport> findAllByStatusOrderByCreatedAtDesc(InconvenienceReportStatus status);
}
