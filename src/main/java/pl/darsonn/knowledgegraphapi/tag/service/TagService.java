package pl.darsonn.knowledgegraphapi.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.darsonn.knowledgegraphapi.exception.ResourceNotFoundException;
import pl.darsonn.knowledgegraphapi.tag.dto.TagDto;
import pl.darsonn.knowledgegraphapi.tag.mapper.TagMapper;
import pl.darsonn.knowledgegraphapi.tag.model.Tag;
import pl.darsonn.knowledgegraphapi.tag.repository.TagRepository;
import pl.darsonn.knowledgegraphapi.user.model.User;
import pl.darsonn.knowledgegraphapi.user.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TagMapper tagMapper;

    @Transactional
    public TagDto createTag(TagDto tagDto, String userLogin) {
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userLogin));

        Tag tag = tagMapper.toEntity(tagDto);
        tag.setUser(user);

        Tag savedTag = tagRepository.save(tag);
        return tagMapper.toDto(savedTag);
    }

    public List<TagDto> getAllTags(String userLogin) {
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userLogin));

        return tagRepository.findByUser(user).stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    public TagDto getTagById(UUID id, String userLogin) {
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userLogin));
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));

        if (!tag.getUser().equals(user)) {
            throw new ResourceNotFoundException("Tag not found with id: " + id);
        }

        return tagMapper.toDto(tag);
    }

    @Transactional
    public TagDto updateTag(UUID id, TagDto tagDto, String userLogin) {
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userLogin));
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));

        if (!tag.getUser().equals(user)) {
            throw new ResourceNotFoundException("Tag not found with id: " + id);
        }

        tag.setName(tagDto.getName());

        Tag updatedTag = tagRepository.save(tag);
        return tagMapper.toDto(updatedTag);
    }

    @Transactional
    public void deleteTag(UUID id, String userLogin) {
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userLogin));
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));

        if (!tag.getUser().equals(user)) {
            throw new ResourceNotFoundException("Tag not found with id: " + id);
        }

        tagRepository.delete(tag);
    }
}
