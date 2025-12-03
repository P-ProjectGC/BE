package plango.member.application.mapper;

import org.springframework.stereotype.Component;
import plango.member.application.dto.response.MemberSearchResponse;
import plango.member.domain.entity.Member;

@Component
public class MemberSearchMapper {

    public MemberSearchResponse toSearchResponse(Member member) {
        return new MemberSearchResponse(
                member.getId(),
                member.getNickname(),
                member.getProfileImageUrl()
        );
    }
}
