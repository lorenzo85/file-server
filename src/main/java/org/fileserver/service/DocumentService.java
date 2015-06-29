package org.fileserver.service;

import org.fileserver.dto.DocumentDto;

import java.io.IOException;
import java.util.List;

public interface DocumentService {

    DocumentDto save(DocumentDto dto) throws IOException;

    List<DocumentDto> listAll();

    DocumentDto load(String fileId) throws IOException;

    void delete(String documentIdentifier);

}
