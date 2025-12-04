package plango.member.application.mapper;

import org.springframework.stereotype.Component;
import plango.auth.application.dto.request.MemberSignUpRequest;
import plango.auth.application.dto.response.MemberSignUpResponse;
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
                member.getProfileImageUrl(),
                member.getLoginType()
        );
    }

    public Member toEntity(MemberSignUpRequest request) {
        return Member.createLocalMember(
                request.name(),
                request.loginId(),
                request.email(),
                request.password(),
                request.nickname()
        );
    }

    public MemberSignUpResponse toSignUpResponse(Member member) {
        return new MemberSignUpResponse(
                member.getId(),
                member.getName(),
                member.getNickname(),
                member.getLoginId(),
                member.getEmail()
        );
    }
}
