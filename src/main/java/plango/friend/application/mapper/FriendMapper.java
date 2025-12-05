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
                friend.getRequester()
                        .getId(),
                friend.getReceiver()
                        .getId(),
                friend.getReceiver()
                        .getNickname(),
                friend.getStatus()
                        .name()
        );
    }

    public FriendListItemResponse toFriendListItemResponse(Friend friend, Long currentMemberId) {
        Member targetMember = getTargetMember(friend, currentMemberId);

        return new FriendListItemResponse(
                friend.getId(),
                targetMember.getId(),
                targetMember.getName(),
                targetMember.getNickname(),
                targetMember.getProfileImageUrl(),
                targetMember.getLoginType()
                        .name()
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

    private Member getTargetMember(Friend friend, Long currentMemberId) {
        Member requester = friend.getRequester();
        Member receiver = friend.getReceiver();

        if (requester.getId()
                .equals(currentMemberId)) {
            return receiver;
        }

        return requester;
    }
}
