package plango.notice.application.mapper;

import java.util.List;

import plango.notice.domain.entity.Notice;
import plango.notice.application.dto.request.NoticeCreateRequest;
import plango.notice.application.dto.response.NoticeListResponse;
import plango.notice.application.dto.response.NoticeResponse;

public class NoticeMapper {

    // 공지 생성 시 DTO -> Entity
    public static Notice toEntity(NoticeCreateRequest request) {
        return Notice.builder()
                .title(request.title())
                .content(request.content())
                .build();
    }

    // 공지 상세 조회 Entity -> DTO
    public static NoticeResponse toResponse(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    // 공지 목록 조회 Entity -> ListResponse
    public static NoticeListResponse toListResponse(Notice notice) {
        return NoticeListResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    // 리스트 변환
    public static List<NoticeListResponse> toListResponses(List<Notice> notices) {
        return notices.stream()
                .map(NoticeMapper::toListResponse)
                .toList();
    }
}
