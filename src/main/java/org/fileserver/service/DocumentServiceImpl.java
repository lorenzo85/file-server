package org.fileserver.service;

import org.fileserver.domain.File;
import org.fileserver.dto.DocumentDto;
import org.fileserver.repository.FileDatabaseRepository;
import org.fileserver.repository.FileSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
@Transactional(rollbackFor = IOException.class)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentFileMapper mapper;
    private final FileSystemRepository fileSystemRepository;
    private final FileDatabaseRepository databaseRepository;
    private final AsyncDocumentDeleteService asyncDocumentDeleteService;

    @Autowired
    public DocumentServiceImpl(DocumentFileMapper mapper, FileDatabaseRepository databaseRepository,
                               FileSystemRepository fileSystemRepository, AsyncDocumentDeleteService asyncDocumentDeleteService) {
        this.mapper = mapper;
        this.databaseRepository = databaseRepository;
        this.fileSystemRepository = fileSystemRepository;
        this.asyncDocumentDeleteService = asyncDocumentDeleteService;
    }

    @Override
    public DocumentDto save(DocumentDto dto) throws IOException {
        File file = mapper.map(dto);
        file = databaseRepository.save(file);
        fileSystemRepository.save(file);
        return mapper.map(file);
    }

    @Override
    public List<DocumentDto> listAll() {
        List<File> files = databaseRepository.findAllNotDeleted();
        return mapper.map(files);
    }

    @Override
    public DocumentDto load(String fileId) throws IOException {
        checkNotNull(fileId);
        File file = databaseRepository.findOne(fileId);

        checkNotNull(file, "The file with id=%s does not exists!");
        checkArgument(!file.isDeleted(), "The file with id=%s was scheduled for deletion!");

        fileSystemRepository.load(file);
        return mapper.map(file);
    }

    @Override
    public void delete(String documentId) {
        databaseRepository.markFileAsDeleted(documentId);
        asyncDocumentDeleteService.deleteAsync(documentId);
    }

}
