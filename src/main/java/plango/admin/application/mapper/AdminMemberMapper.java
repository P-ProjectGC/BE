package plango.admin.application.mapper;

import plango.admin.application.dto.response.AdminMemberDetailResponse;
import plango.admin.application.dto.response.AdminMemberSummaryResponse;
import plango.member.domain.entity.Member;

import java.util.List;

public class AdminMemberMapper {

    private AdminMemberMapper() {
    }

    public static AdminMemberSummaryResponse toSummaryResponse(Member member) {
        return AdminMemberSummaryResponse.builder()
                .memberId(member.getId())
                .loginId(member.getLoginId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .loginType(member.getLoginType().name())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static List<AdminMemberSummaryResponse> toSummaryResponses(List<Member> members) {
        return members.stream()
                .map(AdminMemberMapper::toSummaryResponse)
                .toList();
    }

    public static AdminMemberDetailResponse toDetailResponse(Member member) {
        return AdminMemberDetailResponse.builder()
                .memberId(member.getId())
                .loginId(member.getLoginId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .loginType(member.getLoginType().name())
                .profileImageUrl(member.getProfileImageUrl())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
