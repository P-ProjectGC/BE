package plango.friend.application.usecase;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.friend.application.dto.response.FriendRequestListItemResponse;
import plango.friend.application.mapper.FriendMapper;
import plango.friend.domain.entity.Friend;
import plango.friend.domain.service.FriendService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberGetService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendReceivedRequestListQueryUseCase {

    private final FriendService friendService;

    private final FriendMapper friendMapper;

    private final MemberGetService memberGetService;

    public List<FriendRequestListItemResponse> execute(Long memberId) {
        Member member = memberGetService.getById(memberId);

        List<Friend> friends = friendService.getReceivedRequestedFriends(member);

        return friends.stream()
                .map(friendMapper::toReceivedFriendRequestListItemResponse)
                .collect(Collectors.toList());
    }
}
