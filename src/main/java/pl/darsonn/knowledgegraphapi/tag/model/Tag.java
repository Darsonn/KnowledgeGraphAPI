package pl.darsonn.knowledgegraphapi.tag.model;

import jakarta.persistence.*;
import lombok.*;
import pl.darsonn.knowledgegraphapi.memory.model.Memory;
import pl.darsonn.knowledgegraphapi.user.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "tags")
    @Builder.Default
    private Set<Memory> memories = new HashSet<>();
}