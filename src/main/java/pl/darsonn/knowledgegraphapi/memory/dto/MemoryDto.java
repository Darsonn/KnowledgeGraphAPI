package pl.darsonn.knowledgegraphapi.memory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.darsonn.knowledgegraphapi.tag.dto.TagDto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoryDto {
    private UUID id;
    private String content;
    private LocalDateTime createdAt;
    private Set<TagDto> tags;
}
