package plango.room.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.room.domain.entity.Room;
import plango.room.domain.repository.RoomRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomSaveService {

    private final RoomRepository roomRepository;

    public Room save(Room room) {
        return roomRepository.save(room);
    }
}
