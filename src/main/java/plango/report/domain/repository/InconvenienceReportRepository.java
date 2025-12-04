package plango.report.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.report.domain.entity.InconvenienceReport;

public interface InconvenienceReportRepository
        extends JpaRepository<InconvenienceReport, Long> {
}
