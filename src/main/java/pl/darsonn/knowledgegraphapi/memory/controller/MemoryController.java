package pl.darsonn.knowledgegraphapi.memory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.darsonn.knowledgegraphapi.memory.dto.MemoryDto;
import pl.darsonn.knowledgegraphapi.memory.service.MemoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/memories")
@RequiredArgsConstructor
public class MemoryController {

    private final MemoryService memoryService;

    @PostMapping
    public ResponseEntity<MemoryDto> createMemory(@RequestBody MemoryDto memoryDto, Authentication authentication) {
        MemoryDto createdMemory = memoryService.createMemory(memoryDto, authentication.getName());
        return new ResponseEntity<>(createdMemory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemoryDto>> getAllMemories(Authentication authentication) {
        List<MemoryDto> memories = memoryService.getAllMemories(authentication.getName());
        return ResponseEntity.ok(memories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoryDto> getMemoryById(@PathVariable UUID id, Authentication authentication) {
        MemoryDto memory = memoryService.getMemoryById(id, authentication.getName());
        return ResponseEntity.ok(memory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoryDto> updateMemory(@PathVariable UUID id, @RequestBody MemoryDto memoryDto, Authentication authentication) {
        MemoryDto updatedMemory = memoryService.updateMemory(id, memoryDto, authentication.getName());
        return ResponseEntity.ok(updatedMemory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemory(@PathVariable UUID id, Authentication authentication) {
        memoryService.deleteMemory(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
