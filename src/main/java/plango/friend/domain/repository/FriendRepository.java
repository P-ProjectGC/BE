package plango.friend.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import plango.friend.domain.entity.Friend;
import plango.member.domain.entity.Member;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByRequesterAndReceiver(Member requester, Member receiver);

    boolean existsByRequesterAndReceiverOrRequesterAndReceiver(
            Member requester,
            Member receiver,
            Member reversedRequester,
            Member reversedReceiver
    );

    Optional<Friend> findByRequesterAndReceiver(Member requester, Member receiver);
}
