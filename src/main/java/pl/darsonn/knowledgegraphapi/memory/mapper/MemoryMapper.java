package pl.darsonn.knowledgegraphapi.memory.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.darsonn.knowledgegraphapi.memory.dto.MemoryDto;
import pl.darsonn.knowledgegraphapi.memory.model.Memory;
import pl.darsonn.knowledgegraphapi.tag.mapper.TagMapper;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemoryMapper {

    private final TagMapper tagMapper;

    public MemoryDto toDto(Memory memory) {
        if (memory == null) {
            return null;
        }
        return MemoryDto.builder()
                .id(memory.getId())
                .content(memory.getContent())
                .createdAt(memory.getCreatedAt())
                .tags(memory.getTags().stream().map(tagMapper::toDto).collect(Collectors.toSet()))
                .build();
    }

    public Memory toEntity(MemoryDto memoryDto) {
        if (memoryDto == null) {
            return null;
        }
        return Memory.builder()
                .id(memoryDto.getId())
                .content(memoryDto.getContent())
                .tags(memoryDto.getTags().stream().map(tagMapper::toEntity).collect(Collectors.toSet()))
                .build();
    }
}
