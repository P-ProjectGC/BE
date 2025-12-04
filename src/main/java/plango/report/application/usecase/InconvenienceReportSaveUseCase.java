package plango.report.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberGetService;
import plango.report.application.dto.request.InconvenienceReportSaveRequest;
import plango.report.application.dto.response.InconvenienceReportSaveResponse;
import plango.report.application.mapper.InconvenienceReportMapper;
import plango.report.domain.entity.InconvenienceReport;
import plango.report.domain.service.InconvenienceReportSaveService;

@Service
@RequiredArgsConstructor
public class InconvenienceReportSaveUseCase {

    private final MemberGetService memberGetService;
    private final InconvenienceReportSaveService inconvenienceReportSaveService;

    @Transactional
    public InconvenienceReportSaveResponse execute(
            Long memberId,
            InconvenienceReportSaveRequest request
    ) {

        Member member = memberGetService.getById(memberId);

        InconvenienceReport report =
                InconvenienceReportMapper.toEntity(member, request);

        InconvenienceReport saved =
                inconvenienceReportSaveService.save(report);

        return InconvenienceReportMapper.toResponse(saved);
    }
}
