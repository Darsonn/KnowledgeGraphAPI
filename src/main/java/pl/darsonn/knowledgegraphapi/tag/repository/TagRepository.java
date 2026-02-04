package pl.darsonn.knowledgegraphapi.tag.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.darsonn.knowledgegraphapi.tag.model.Tag;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    // Szukanie tagów konkretnego użytkownika (podstawowe wyszukiwanie)
    List<Tag> findAllByUserIdAndNameContainingIgnoreCase(UUID userId, String name);

    // Kluczowe zapytanie: Pobierz najpopularniejsze tagi użytkownika
    @Query("SELECT t FROM Tag t " +
            "LEFT JOIN t.memories m " +
            "WHERE t.user.id = :userId " +
            "GROUP BY t.id " +
            "ORDER BY COUNT(m) DESC")
    List<Tag> findMostPopularTags(@Param("userId") UUID userId, Pageable pageable);
}