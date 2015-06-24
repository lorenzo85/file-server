package org.fileserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository repository;

    @Override
    public void save(Document document) throws IOException {
        repository.save(document);
    }

    @Override
    public List<DocumentMetadata> findAll() throws IOException {
        return repository.findAll();
    }
}
