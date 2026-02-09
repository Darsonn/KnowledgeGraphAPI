package pl.darsonn.knowledgegraphapi.tag.mapper;

import org.springframework.stereotype.Component;
import pl.darsonn.knowledgegraphapi.tag.dto.TagDto;
import pl.darsonn.knowledgegraphapi.tag.model.Tag;

@Component
public class TagMapper {

    public TagDto toDto(Tag tag) {
        if (tag == null) {
            return null;
        }
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public Tag toEntity(TagDto tagDto) {
        if (tagDto == null) {
            return null;
        }
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }
}
