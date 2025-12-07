package plango.notice.application.mapper;

import java.util.List;
import plango.notice.application.dto.request.NoticeCreateRequest;
import plango.notice.application.dto.response.NoticeListResponse;
import plango.notice.application.dto.response.NoticeMemberListResponse;
import plango.notice.application.dto.response.NoticeResponse;
import plango.notice.domain.entity.Notice;

public class NoticeMapper {

    public static Notice toEntity(NoticeCreateRequest request, Long adminId) {
        return Notice.builder()
                .title(request.title())
                .content(request.content())
                .adminId(adminId)
                .type(request.type())
                .build();
    }

    public static NoticeResponse toResponse(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .adminId(notice.getAdminId())
                .type(notice.getType())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    public static NoticeListResponse toListResponse(Notice notice) {
        return NoticeListResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .type(notice.getType())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    public static List<NoticeListResponse> toListResponses(List<Notice> notices) {
        return notices.stream()
                .map(NoticeMapper::toListResponse)
                .toList();
    }

    // ===== 사용자용 매핑 =====
    public static NoticeMemberListResponse toMemberListResponse(Notice notice) {
        return NoticeMemberListResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .type(notice.getType())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    public static List<NoticeMemberListResponse> toMemberListResponses(List<Notice> notices) {
        return notices.stream()
                .map(NoticeMapper::toMemberListResponse)
                .toList();
    }
}
