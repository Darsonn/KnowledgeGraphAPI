package pl.darsonn.knowledgegraphapi.tag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.darsonn.knowledgegraphapi.tag.dto.TagDto;
import pl.darsonn.knowledgegraphapi.tag.service.TagService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto, Authentication authentication) {
        TagDto createdTag = tagService.createTag(tagDto, authentication.getName());
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags(Authentication authentication) {
        List<TagDto> tags = tagService.getAllTags(authentication.getName());
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable UUID id, Authentication authentication) {
        TagDto tag = tagService.getTagById(id, authentication.getName());
        return ResponseEntity.ok(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(@PathVariable UUID id, @RequestBody TagDto tagDto, Authentication authentication) {
        TagDto updatedTag = tagService.updateTag(id, tagDto, authentication.getName());
        return ResponseEntity.ok(updatedTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id, Authentication authentication) {
        tagService.deleteTag(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
