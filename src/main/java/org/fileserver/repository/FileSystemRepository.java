package org.fileserver.repository;

import org.fileserver.domain.File;

import java.io.IOException;

public interface FileSystemRepository {

    void save(File file) throws IOException;

    void load(File file) throws IOException;

    void delete(File file) throws IOException;
}
