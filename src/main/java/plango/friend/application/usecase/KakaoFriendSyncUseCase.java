package plango.friend.application.usecase;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.response.KakaoFriendListResponse;
import plango.auth.domain.service.KakaoAuthService;
import plango.auth.domain.service.SocialAccountService;
import plango.friend.application.dto.request.KakaoFriendSyncRequest;
import plango.friend.application.dto.response.FriendResponse;
import plango.friend.application.exception.FriendErrorCode;
import plango.friend.application.exception.FriendException;
import plango.friend.application.mapper.FriendMapper;
import plango.friend.domain.entity.Friend;
import plango.friend.domain.service.FriendService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberGetService;

@Service
@RequiredArgsConstructor
public class KakaoFriendSyncUseCase {

    private static final String PROVIDER_KAKAO = "KAKAO";

    private final KakaoAuthService kakaoAuthService;
    private final SocialAccountService socialAccountService;
    private final FriendService friendService;
    private final FriendMapper friendMapper;
    private final MemberGetService memberGetService;

    @Transactional
    public List<FriendResponse> execute(Long memberId, KakaoFriendSyncRequest request) {
        Member requester = memberGetService.getById(memberId);

        KakaoFriendListResponse kakaoFriendListResponse =
                kakaoAuthService.getFriendsByAuthorizationCode(request.authorizationCode());

        if (kakaoFriendListResponse == null
                || kakaoFriendListResponse.elements() == null
                || kakaoFriendListResponse.elements().isEmpty()) {
            return List.of();
        }

        List<String> kakaoFriendIds = kakaoFriendListResponse.elements()
                .stream()
                .map(KakaoFriendListResponse.KakaoFriendSummary::id)
                .toList();

        List<Member> candidateMembers =
                socialAccountService.findMembersByProviderAndProviderUserIds(
                        PROVIDER_KAKAO,
                        kakaoFriendIds
                );

        List<FriendResponse> responses = new ArrayList<>();

        for (Member candidate : candidateMembers) {
            if (requester.getId().equals(candidate.getId())) {
                continue;
            }

            try {
                Friend friend = friendService.requestFriend(requester, candidate);
                friend.accept();
                responses.add(friendMapper.toFriendResponse(friend));
            } catch (FriendException exception) {
                FriendErrorCode errorCode = exception.getErrorCode();

                if (errorCode == FriendErrorCode.FRIEND_REQUEST_ALREADY_EXISTS
                        || errorCode == FriendErrorCode.FRIEND_ALREADY_EXISTS) {
                    continue;
                }

                throw exception;
            }
        }

        return responses;
    }
}
