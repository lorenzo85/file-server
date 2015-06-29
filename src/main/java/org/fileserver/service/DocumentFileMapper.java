package org.fileserver.service;

import org.fileserver.domain.File;
import org.fileserver.domain.Metadata;
import org.fileserver.dto.DocumentDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DocumentFileMapper {

    public List<DocumentDto> map(List<File> documents) {
        return documents
                .stream()
                .map(this::map)
                .collect(toList());
    }

    public DocumentDto map(File file) {
        Metadata metadata = file.getMetadata();
        return new DocumentDto(file.getId(), file.getBytes(), metadata.getFileName(), metadata.getMediaType());
    }

    public File map(DocumentDto dto) {
        File file = new File(dto.getBytes());
        Metadata metadata = new Metadata(file, dto.getFileName(), dto.getMediaType());
        file.setMetadata(metadata);
        return file;
    }
}
