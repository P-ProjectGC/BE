package plango.member.application.mapper;

import org.springframework.stereotype.Component;
import plango.member.application.dto.response.MemberProfileResponse;
import plango.member.domain.entity.Member;

@Component
public class MemberMapper {

    public MemberProfileResponse toProfileResponse(Member member) {
        return new MemberProfileResponse(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getLoginId(),
                member.getProfileImageUrl()
        );
    }
}
