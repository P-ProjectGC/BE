package plango.friend.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.friend.application.dto.request.FriendRequestRequest;
import plango.friend.application.dto.response.FriendResponse;
import plango.friend.application.exception.FriendErrorCode;
import plango.friend.application.exception.FriendException;
import plango.friend.application.mapper.FriendMapper;
import plango.friend.domain.entity.Friend;
import plango.friend.domain.service.FriendSaveService;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;
import plango.member.domain.service.MemberGetService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendRequestUseCase {

    private final MemberGetService memberGetService;
    private final MemberRepository memberRepository;
    private final FriendSaveService friendSaveService;
    private final FriendMapper friendMapper;

    @Transactional
    public FriendResponse execute(Long requesterId, FriendRequestRequest request) {
        Member requester = memberGetService.getById(requesterId);

        Member receiver = memberRepository.findByNickname(request.targetNickname())
                .orElseThrow(() -> new FriendException(FriendErrorCode.FRIEND_TARGET_NOT_FOUND));

        Friend friend = friendSaveService.requestFriend(requester, receiver);

        return friendMapper.toFriendResponse(friend);
    }
}
