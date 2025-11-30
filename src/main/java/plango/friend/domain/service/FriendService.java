package plango.friend.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.friend.application.exception.FriendErrorCode;
import plango.friend.application.exception.FriendException;
import plango.friend.domain.entity.Friend;
import plango.friend.domain.entity.FriendStatus;
import plango.friend.domain.repository.FriendRepository;
import plango.member.domain.entity.Member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

    private final FriendRepository friendRepository;

    @Transactional
    public Friend requestFriend(Member requester, Member receiver) {
        validateNotSelf(requester, receiver);
        validateFriendRelation(requester, receiver);
        Friend friend = Friend.request(requester, receiver);
        return friendRepository.save(friend);
    }

    @Transactional
    public Friend acceptFriend(Long memberId, Long friendId) {
        Friend friend = getFriendForReceiver(memberId, friendId);
        validateRequestedStatus(friend);
        friend.accept();
        return friend;
    }

    @Transactional
    public void rejectFriend(Long memberId, Long friendId) {
        Friend friend = getFriendForReceiver(memberId, friendId);
        validateRequestedStatus(friend);
        friendRepository.delete(friend);
    }

    @Transactional
    public void cancelFriendRequest(Long memberId, Long friendId) {
        Friend friend = getFriendForRequester(memberId, friendId);
        validateRequestedStatus(friend);
        friendRepository.delete(friend);
    }

    private Friend getFriendForReceiver(Long memberId, Long friendId) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new FriendException(FriendErrorCode.FRIEND_NOT_FOUND));

        if (!friend.getReceiver().getId().equals(memberId)) {
            throw new FriendException(FriendErrorCode.FRIEND_NOT_FOUND);
        }

        return friend;
    }

    private Friend getFriendForRequester(Long memberId, Long friendId) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new FriendException(FriendErrorCode.FRIEND_NOT_FOUND));

        if (!friend.getRequester().getId().equals(memberId)) {
            throw new FriendException(FriendErrorCode.FRIEND_NOT_FOUND);
        }

        return friend;
    }

    private void validateRequestedStatus(Friend friend) {
        if (friend.getStatus() != FriendStatus.REQUESTED) {
            throw new FriendException(FriendErrorCode.INVALID_FRIEND_STATUS);
        }
    }

    private void validateNotSelf(Member requester, Member receiver) {
        if (requester.getId().equals(receiver.getId())) {
            throw new FriendException(FriendErrorCode.CANNOT_REQUEST_SELF);
        }
    }

    private void validateFriendRelation(Member requester, Member receiver) {
        boolean exists = friendRepository.existsByRequesterAndReceiverOrRequesterAndReceiver(
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
