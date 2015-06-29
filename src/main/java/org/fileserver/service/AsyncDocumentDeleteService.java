package org.fileserver.service;

import org.apache.log4j.Logger;
import org.fileserver.domain.File;
import org.fileserver.repository.FileDatabaseRepository;
import org.fileserver.repository.FileSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static java.lang.String.format;

@Service
public class AsyncDocumentDeleteService {

    private static final Logger LOGGER = Logger.getLogger(AsyncDocumentDeleteService.class);

    @Autowired
    private FileSystemRepository fileSystemRepository;
    @Autowired
    private FileDatabaseRepository fileDatabaseRepository;

    @Async
    public void deleteAsync(String documentId) {
        File fileToBeDeleted = new File(documentId);
        tryToDelete(fileToBeDeleted);
    }

    @Scheduled(cron = "0 0 12 1/1 * ?")
    public void deleteAllMarkedAsDeleted() {
        tryToDelete(fileDatabaseRepository.findAllDeleted());
    }

    private void tryToDelete(List<File> filesToDelete) {
        if (!filesToDelete.isEmpty()) {
            LOGGER.info(format("File scheduler: trying to delete %d files", filesToDelete.size()));
            filesToDelete.forEach(this::tryToDelete);
        } else {
            LOGGER.info("File scheduler: no files to be deleted.");
        }
    }

    private void tryToDelete(File fileToDelete) {
        String documentId = fileToDelete.getId();
        try {
            fileSystemRepository.delete(fileToDelete);
            fileDatabaseRepository.delete(documentId);
            LOGGER.info(format("file=%s successfully deleted", documentId));
        } catch (NoSuchFileException e) {
            LOGGER.warn(format("file=%s, tried to delete it but didn't exist. Removing entry from database.", documentId));
            fileDatabaseRepository.delete(documentId);
        } catch (IOException e) {
            LOGGER.warn(format("file=%s could not be deleted", documentId), e);
        }
    }
}
