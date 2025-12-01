package plango.friend.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.friend.domain.service.FriendService;

@Service
@RequiredArgsConstructor
public class FriendCancelUseCase {

    private final FriendService friendService;

    @Transactional
    public void execute(Long memberId, Long friendId) {
        friendService.cancelFriendRequest(memberId, friendId);
    }
}
