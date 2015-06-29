package org.fileserver.repository;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Component
public class FileSystemIO {
    /**
     * Reads a file from the file system.
     *
     * @param   first
     *          the path string or initial part of the path string
     * @param   more
     *          additional strings to be joined to form the path string
     *
     * @return  a byte array containing the bytes read from the file
     *
     * @throws IOException
     */
    public byte[] read(String first, String more) throws IOException {
        Path documentPath = Paths.get(first, more);
        return Files.readAllBytes(documentPath);
    }

    /**
     * Saves the byte array to a file.
     *
     * @param   bytes
     *          the byte array with the bytes to write
     * @param   first
     *          the path string or initial part of the path string
     * @param   more
     *          additional strings to be joined to form the path string
     *
     * @throws IOException
     */
    public void save(byte[] bytes, String first, String more) throws IOException {
        Path path = Paths.get(first, more);
        Files.write(path, bytes);
    }

    /**
     * Deletes a file.
     *
     * @param   first
     *          the path string or initial part of the path string
     * @param   more
     *          additional strings to be joined to form the path string
     *
     * @throws IOException
     */
    public void delete(String first, String more) throws IOException {
        Path filePath = Paths.get(first, more);
        Files.delete(filePath);
    }

    /**
     * Creates a directory.
     *
     * @param   directoryPath
     *          the path string of the directory to be created
     *
     * @throws IOException
     */
    public void createDirectory(String directoryPath) throws IOException {
        File file = new File(directoryPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException(format("Could not create directory: %s", directoryPath));
            }
        }
    }

    /**
     * Lists the content of a directory.
     *
     * @param   directoryPath
     *          the path string of the directory from which the files will be listed from
     *
     * @return a list of strings containing the files and directory found in the directoryPath
     */
    public List<String> listDirectory(String directoryPath, FilenameFilter filenameFilter) {
        File documentsFolder = new File(directoryPath);
        return Arrays.asList(documentsFolder.list(filenameFilter));
    }
}
