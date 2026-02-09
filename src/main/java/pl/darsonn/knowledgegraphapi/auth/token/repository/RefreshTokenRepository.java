package pl.darsonn.knowledgegraphapi.auth.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.darsonn.knowledgegraphapi.auth.token.model.RefreshToken;
import pl.darsonn.knowledgegraphapi.user.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);
}
