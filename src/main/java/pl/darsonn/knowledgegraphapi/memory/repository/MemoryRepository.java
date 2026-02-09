package pl.darsonn.knowledgegraphapi.memory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.darsonn.knowledgegraphapi.memory.model.Memory;
import pl.darsonn.knowledgegraphapi.user.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, UUID> {
    List<Memory> findAllByUserIdOrderByCreatedAtDesc(UUID userId);
    List<Memory> findAllByUserIdAndTagsId(UUID userId, UUID tagId);
    List<Memory> findAllByUserIdAndContentContainingIgnoreCase(UUID userId, String keyword);
    List<Memory> findByUser(User user);
}