package plango.friend.application.mapper;

import org.springframework.stereotype.Component;
import plango.friend.application.dto.response.FriendListItemResponse;
import plango.friend.application.dto.response.FriendRequestListItemResponse;
import plango.friend.application.dto.response.FriendResponse;
import plango.friend.domain.entity.Friend;
import plango.member.domain.entity.Member;

@Component
public class FriendMapper {

    public FriendResponse toFriendResponse(Friend friend) {
        return new FriendResponse(
                friend.getId(),
                friend.getRequester().getId(),
                friend.getReceiver().getId(),
                friend.getReceiver().getNickname(),
                friend.getStatus().name()
        );
    }

    public FriendListItemResponse toFriendListItemResponse(Friend friend, Long currentMemberId) {
        Member opponent = friend.getRequester().getId().equals(currentMemberId)
                ? friend.getReceiver()
                : friend.getRequester();

        return new FriendListItemResponse(
                friend.getId(),
                opponent.getId(),
                opponent.getNickname(),
                opponent.getProfileImageUrl()
        );
    }

    public FriendRequestListItemResponse toReceivedFriendRequestListItemResponse(Friend friend) {
        Member requester = friend.getRequester();

        return new FriendRequestListItemResponse(
                friend.getId(),
                requester.getId(),
                requester.getNickname(),
                requester.getProfileImageUrl(),
                friend.getCreatedAt()
        );
    }

    public FriendRequestListItemResponse toSentFriendRequestListItemResponse(Friend friend) {
        Member receiver = friend.getReceiver();

        return new FriendRequestListItemResponse(
                friend.getId(),
                receiver.getId(),
                receiver.getNickname(),
                receiver.getProfileImageUrl(),
                friend.getCreatedAt()
        );
    }
}
