package plango.room.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.member.domain.entity.Member;
import plango.room.domain.entity.Room;
import plango.room.domain.entity.RoomMember;
import plango.room.domain.entity.RoomRole;
import plango.room.domain.repository.RoomMemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomMemberSaveService {

    private final RoomMemberRepository roomMemberRepository;

    public RoomMember addOwner(Room room, Member owner) {
        RoomMember roomMember = RoomMember.createOwner(room, owner);
        return roomMemberRepository.save(roomMember);
    }

    public RoomMember addMember(Room room, Member member) {
        RoomMember roomMember = RoomMember.createMember(room, member);
        return roomMemberRepository.save(roomMember);
    }
}
