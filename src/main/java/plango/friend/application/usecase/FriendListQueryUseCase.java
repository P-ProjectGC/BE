package plango.friend.application.usecase;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.friend.application.dto.response.FriendListItemResponse;
import plango.friend.application.mapper.FriendMapper;
import plango.friend.domain.entity.Friend;
import plango.friend.domain.service.FriendService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberGetService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendListQueryUseCase {

    private final FriendService friendService;
    private final FriendMapper friendMapper;
    private final MemberGetService memberGetService;

    public List<FriendListItemResponse> execute(Long memberId, String nickname) {
        Member member = memberGetService.getById(memberId);
        List<Friend> friends = friendService.getAcceptedFriends(member);

        return friends.stream()
                .map(friend -> friendMapper.toFriendListItemResponse(friend, memberId))
                .filter(response -> isNicknameMatched(response.nickname(), nickname))
                .collect(Collectors.toList());
    }

    private boolean isNicknameMatched(String targetNickname, String keyword) {
        if (keyword == null) {
            return true;
        }

        if (keyword.isBlank()) {
            return true;
        }

        return targetNickname != null && targetNickname.contains(keyword);
    }
}
