package pl.darsonn.knowledgegraphapi.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.darsonn.knowledgegraphapi.user.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserResponse {
    private UUID id;
    private String email;
    private String displayName;
    private User.UserRole role;
    private User.AuthProvider provider;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
