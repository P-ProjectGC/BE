package plango.friend.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.friend.application.exception.FriendErrorCode;
import plango.friend.application.exception.FriendException;
import plango.friend.domain.entity.Friend;
import plango.friend.domain.repository.FriendRepository;
import plango.member.domain.entity.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendSaveService {

    private final FriendRepository friendRepository;

    @Transactional
    public Friend requestFriend(Member requester, Member receiver) {
        validateNotSelf(requester, receiver);
        validateFriendRelation(requester, receiver);

        Friend friend = Friend.request(requester, receiver);
        return friendRepository.save(friend);
    }

    private void validateNotSelf(Member requester, Member receiver) {
        if (requester.getId().equals(receiver.getId())) {
            throw new FriendException(FriendErrorCode.CANNOT_REQUEST_SELF);
        }
    }

    private void validateFriendRelation(Member requester, Member receiver) {
        boolean exists =
                friendRepository.existsByRequesterAndReceiverOrRequesterAndReceiver(
                        requester,
                        receiver,
                        receiver,
                        requester
                );

        if (exists) {
            throw new FriendException(FriendErrorCode.FRIEND_REQUEST_ALREADY_EXISTS);
        }
    }
}
