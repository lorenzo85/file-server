package org.fileserver.repository;

import org.apache.commons.lang.SerializationUtils;
import org.apache.log4j.Logger;
import org.fileserver.domain.Document;
import org.fileserver.domain.DocumentMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Component
public class FileRepositoryImpl implements FileRepository {

    private static final Logger LOGGER = Logger.getLogger(FileRepositoryImpl.class);

    @Value("${file.folder}")
    private String baseFolder;

    @PostConstruct
    public void init() throws IOException {
        createDirectory(baseFolder);
    }

    @Override
    public Document save(Document document) throws IOException {
        checkNotNull(document.getBytes());
        checkNotNull(document.getMetadata());
        checkNotNull(document.getIdentifier());

        saveDocumentAndMetadata(document);
        return document;
    }

    @Override
    public Document load(String documentIdentifier) throws IOException {
        checkNotNull(documentIdentifier);

        Path documentPath = Paths.get(baseFolder, documentIdentifier);
        byte[] bytes = Files.readAllBytes(documentPath);
        String metadataFileName = getMetadataFileName(documentIdentifier);
        DocumentMetadata metadata = readMetadata(metadataFileName);
        return new Document(bytes, documentIdentifier, metadata);
    }

    @Override
    public List<Document> listAll() throws IOException {
        return listFiles()
                .stream().map(file -> {
                    String metadataFileName = getMetadataFileName(file);
                    DocumentMetadata metadata = readMetadata(metadataFileName);
                    return new Document(file, metadata);
                }).collect(Collectors.toList());
    }

    @Override
    public void delete(String documentIdentifier) throws IOException {
        Path documentPath = Paths.get(baseFolder, documentIdentifier);
        Files.delete(documentPath);

        Path documentMetadataPath = Paths.get(baseFolder, getMetadataFileName(documentIdentifier));
        Files.delete(documentMetadataPath);
    }

    List<String> listFiles() {
        File documentsFolder = new File(baseFolder);
        String[] files = documentsFolder.list((dir, name) ->
                !name.endsWith(DocumentMetadata.getMetadataExtension()));
        return Arrays.asList(files);
    }

    void saveDocumentAndMetadata(Document document) throws IOException {
        byte[] data = document.getBytes();
        String documentFileName = document.getIdentifier();
        save(data, baseFolder, documentFileName);

        DocumentMetadata metaData = document.getMetadata();
        byte[] metaDataBytes = SerializationUtils.serialize(metaData);
        String metadataFileName = getMetadataFileName(documentFileName);
        save(metaDataBytes, baseFolder, metadataFileName);
    }

    /**
     * Reads metadata from the given metadata file name.
     * @param fileName the name of the file from which metadata may be read.
     * @return the metadata object or null if could not be read.
     */
    DocumentMetadata readMetadata(String fileName) {
        Path filePath = Paths.get(baseFolder, fileName);
        try {
            byte[] bytes = Files.readAllBytes(filePath);
            return (DocumentMetadata) SerializationUtils.deserialize(bytes);
        } catch (IOException e) {
            LOGGER.warn(format("Could not read metadata file [%s]", fileName), e);
            return null;
        }
    }

    String getMetadataFileName(String identifier) {
        return format("%s%s", identifier, DocumentMetadata.METADATA_EXT);
    }

    void save(byte[] bytes, String first, String more) throws IOException {
        Path path = Paths.get(first, more);
        Files.write(path, bytes);
    }

    void createDirectory(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new IOException();
            }
        }
    }
}
