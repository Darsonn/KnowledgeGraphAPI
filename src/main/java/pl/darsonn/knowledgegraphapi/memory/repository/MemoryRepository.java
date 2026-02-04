package pl.darsonn.knowledgegraphapi.memory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.darsonn.knowledgegraphapi.memory.model.Memory;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, UUID> {

    // Pobierz wszystkie wspomnienia użytkownika, posortowane od najnowszych
    List<Memory> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

    // Znajdź wspomnienia, które zawierają konkretny tag
    List<Memory> findAllByUserIdAndTagsId(UUID userId, UUID tagId);

    // Wyszukiwanie pełnotekstowe w treści wspomnień
    List<Memory> findAllByUserIdAndContentContainingIgnoreCase(UUID userId, String keyword);
}