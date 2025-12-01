package plango.friend.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.friend.application.dto.response.FriendResponse;
import plango.friend.application.mapper.FriendMapper;
import plango.friend.domain.entity.Friend;
import plango.friend.domain.service.FriendService;

@Service
@RequiredArgsConstructor
public class FriendAcceptUseCase {

    private final FriendService friendService;
    private final FriendMapper friendMapper;

    @Transactional
    public FriendResponse execute(Long memberId, Long friendId) {
        Friend friend = friendService.acceptFriend(memberId, friendId);
        return friendMapper.toFriendResponse(friend);
    }
}
