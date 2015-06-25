package org.fileserver.repository;

import org.fileserver.domain.Document;

import java.io.IOException;
import java.util.List;

public interface FileRepository {
    void init() throws IOException;

    Document save(Document document) throws IOException;

    List<Document> listAll() throws IOException;

    Document load(String documentIdentifier) throws IOException;

    void delete(String documentIdentifier) throws IOException;
}
