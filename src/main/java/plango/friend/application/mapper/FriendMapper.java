package plango.friend.application.mapper;

import org.springframework.stereotype.Component;
import plango.friend.application.dto.response.FriendResponse;
import plango.friend.domain.entity.Friend;

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
}
