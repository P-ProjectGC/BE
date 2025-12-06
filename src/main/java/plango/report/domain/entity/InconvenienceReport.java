package plango.report.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plango.global.common.entity.BaseTimeEntity;
import plango.member.domain.entity.Member;
import plango.report.domain.entity.InconvenienceReportStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InconvenienceReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InconvenienceReportStatus status;

    @Column(columnDefinition = "TEXT")
    private String adminMemo;

    public InconvenienceReport(Member member, String content) {
        this.member = member;
        this.content = content;
        this.status = InconvenienceReportStatus.WAITING;
    }

    public void changeStatus(InconvenienceReportStatus status, String adminMemo) {
        this.status = status;
        this.adminMemo = adminMemo;
    }
}
