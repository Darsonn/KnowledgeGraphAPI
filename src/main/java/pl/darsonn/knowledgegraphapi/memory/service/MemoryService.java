package pl.darsonn.knowledgegraphapi.memory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.darsonn.knowledgegraphapi.exception.ResourceNotFoundException;
import pl.darsonn.knowledgegraphapi.memory.dto.MemoryDto;
import pl.darsonn.knowledgegraphapi.memory.mapper.MemoryMapper;
import pl.darsonn.knowledgegraphapi.memory.model.Memory;
import pl.darsonn.knowledgegraphapi.memory.repository.MemoryRepository;
import pl.darsonn.knowledgegraphapi.tag.dto.TagDto;
import pl.darsonn.knowledgegraphapi.tag.model.Tag;
import pl.darsonn.knowledgegraphapi.tag.repository.TagRepository;
import pl.darsonn.knowledgegraphapi.user.model.User;
import pl.darsonn.knowledgegraphapi.user.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private final MemoryRepository memoryRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final MemoryMapper memoryMapper;

    @Transactional
    public MemoryDto createMemory(MemoryDto memoryDto, String userLogin) {
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userLogin));

        Memory memory = memoryMapper.toEntity(memoryDto);
        memory.setUser(user);

        Set<Tag> tags = processTags(memoryDto.getTags(), user);
        memory.setTags(tags);

        Memory savedMemory = memoryRepository.save(memory);
        return memoryMapper.toDto(savedMemory);
    }

    public List<MemoryDto> getAllMemories(String userLogin) {
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userLogin));

        return memoryRepository.findByUser(user).stream()
                .map(memoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public MemoryDto getMemoryById(UUID id, String userLogin) {
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userLogin));
        Memory memory = memoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Memory not found with id: " + id));

        if (!memory.getUser().equals(user)) {
            throw new ResourceNotFoundException("Memory not found with id: " + id);
        }

        return memoryMapper.toDto(memory);
    }

    @Transactional
    public MemoryDto updateMemory(UUID id, MemoryDto memoryDto, String userLogin) {
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userLogin));
        Memory memory = memoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Memory not found with id: " + id));

        if (!memory.getUser().equals(user)) {
            throw new ResourceNotFoundException("Memory not found with id: " + id);
        }

        memory.setContent(memoryDto.getContent());

        Set<Tag> tags = processTags(memoryDto.getTags(), user);
        memory.setTags(tags);

        Memory updatedMemory = memoryRepository.save(memory);
        return memoryMapper.toDto(updatedMemory);
    }

    @Transactional
    public void deleteMemory(UUID id, String userLogin) {
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userLogin));
        Memory memory = memoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Memory not found with id: " + id));

        if (!memory.getUser().equals(user)) {
            throw new ResourceNotFoundException("Memory not found with id: " + id);
        }

        memoryRepository.delete(memory);
    }

    private Set<Tag> processTags(Set<TagDto> tagDtos, User user) {
        Set<Tag> tags = new HashSet<>();
        if (tagDtos != null) {
            for (TagDto tagDto : tagDtos) {
                Tag tag = tagRepository.findByNameAndUser(tagDto.getName(), user)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagDto.getName());
                            newTag.setUser(user);
                            return tagRepository.save(newTag);
                        });
                tags.add(tag);
            }
        }
        return tags;
    }
}
