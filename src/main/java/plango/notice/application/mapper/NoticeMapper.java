package plango.notice.application.mapper;

import plango.notice.application.dto.request.NoticeCreateRequest;
import plango.notice.application.dto.response.NoticeListResponse;
import plango.notice.application.dto.response.NoticeResponse;
import plango.notice.domain.entity.Notice;

import java.util.List;

public class NoticeMapper {

    public static Notice toEntity(NoticeCreateRequest request, Long adminId) {
        return Notice.builder()
                .title(request.title())
                .content(request.content())
                .adminId(adminId)
                .build();
    }

    public static NoticeResponse toResponse(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .adminId(notice.getAdminId())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    public static NoticeListResponse toListResponse(Notice notice) {
        return NoticeListResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    public static List<NoticeListResponse> toListResponses(List<Notice> notices) {
        return notices.stream()
                .map(NoticeMapper::toListResponse)
                .toList();
    }
}
