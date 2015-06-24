package org.fileserver;

import java.io.IOException;
import java.util.List;

public interface FileService {
    void save(Document document) throws IOException;
    List<DocumentMetadata> findAll() throws IOException;
}
