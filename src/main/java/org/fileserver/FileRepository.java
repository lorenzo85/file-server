package org.fileserver;

import java.io.IOException;
import java.util.List;

public interface FileRepository {
    void init() throws IOException;
    void save(Document document) throws IOException;
    List<DocumentMetadata> findAll() throws IOException;
}
