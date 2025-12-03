package plango.friend.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.friend.domain.service.FriendService;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendDeleteUseCase {

    private final FriendService friendService;

    public void execute(Long memberId, Long friendId) {
        friendService.deleteFriend(memberId, friendId);
    }
}
