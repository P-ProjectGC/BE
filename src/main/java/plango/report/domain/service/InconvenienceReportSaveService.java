package plango.report.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.report.domain.entity.InconvenienceReport;
import plango.report.domain.repository.InconvenienceReportRepository;

@Service
@RequiredArgsConstructor
public class InconvenienceReportSaveService {

    private final InconvenienceReportRepository inconvenienceReportRepository;

    @Transactional
    public InconvenienceReport save(InconvenienceReport report) {
        return inconvenienceReportRepository.save(report);
    }
}
