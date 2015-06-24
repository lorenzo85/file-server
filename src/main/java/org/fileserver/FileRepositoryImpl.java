package org.fileserver;

import org.apache.commons.lang.SerializationUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.io.File.separator;
import static java.lang.String.format;
import static org.fileserver.DocumentMetadata.METADATA_EXT;

@Component
public class FileRepositoryImpl implements FileRepository {

    private static final Logger LOGGER = Logger.getLogger(FileRepositoryImpl.class);

    @Value("${file.folder}")
    private String fileFolder;

    @PostConstruct
    public void init() throws IOException {
        createDirectory(fileFolder);
    }

    public void save(Document document) throws IOException {
        saveDocument(document);
        saveMetadata(document.getMetadata());
    }

    public List<DocumentMetadata> findAll() throws IOException {
        return list(fileFolder)
                .stream()
                .map(this::<DocumentMetadata>readMetadata)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    List<String> list(String directoryPath) {
        File documentsFolder = new File(directoryPath);
        String[] metaDataFiles = documentsFolder.list((dir, name) -> name.endsWith(METADATA_EXT));
        return Arrays.asList(metaDataFiles);
    }

    void saveMetadata(DocumentMetadata metaData) throws IOException {
        String metaDataPathAndName = createPathName(fileFolder, metaData.getName() + METADATA_EXT);
        byte[] data = SerializationUtils.serialize(metaData);
        save(data, metaDataPathAndName);
    }

    void saveDocument(Document document) throws IOException {
        String filePathAndName = createPathName(fileFolder, document.getMetadata().getName());
        byte[] data = document.getBytes();
        save(data, filePathAndName);
    }

    void save(byte[] bytes, String path) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(path)) {
            outputStream.write(bytes);
        }
    }

    @SuppressWarnings("unchecked")
    <T> Optional<T> readMetadata(String fileName) {
        String metaDataFile = createPathName(fileFolder, fileName);
        try (FileInputStream inputStream = new FileInputStream(metaDataFile)) {
            InputStream buffer = new BufferedInputStream(inputStream);
            return (Optional<T>) Optional.of(SerializationUtils.deserialize(buffer));
        } catch (IOException e) {
            LOGGER.warn(format("Could not read metadata file [%s]", fileName));
            return Optional.empty();
        }
    }

    void createDirectory(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new IOException();
            }
        }
    }

    String createPathName(String path, String fileName) {
        return format("%s%s%s", path, separator, fileName);
    }
}
