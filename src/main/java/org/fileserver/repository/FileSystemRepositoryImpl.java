package org.fileserver.repository;

import org.fileserver.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class FileSystemRepositoryImpl implements FileSystemRepository {

    private String baseFolder;
    private FileSystemIO fileSystemIO;

    @Autowired
    public FileSystemRepositoryImpl(@Value("${file.folder}") String baseFolder, FileSystemIO fileSystemIO) {
        this.baseFolder = baseFolder;
        this.fileSystemIO = fileSystemIO;
    }

    @PostConstruct
    public void init() throws IOException {
        fileSystemIO.createDirectory(baseFolder);
    }

    @Override
    public void save(File file) throws IOException {
        byte[] data = file.getBytes();
        String id = file.getId();
        checkNotNull(data);
        checkNotNull(id);
        fileSystemIO.save(data, baseFolder, id);
    }

    @Override
    public void load(File file) throws IOException {
        String id = file.getId();
        checkNotNull(id);
        byte[] bytes = fileSystemIO.read(baseFolder, id);
        file.setBytes(bytes);
    }

    @Override
    public void delete(File file) throws IOException {
        checkNotNull(file);
        fileSystemIO.delete(baseFolder, file.getId());
    }

}
