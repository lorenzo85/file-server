package org.fileserver.domain;

import org.springframework.http.MediaType;

import java.io.Serializable;

public class DocumentMetadata implements Serializable {

    public static final String METADATA_EXT = ".metadata";
    private static final long serialVersionUID = 4087367405740111826L;

    String fileName;
    MediaType mediaType;

    public DocumentMetadata(String fileName, MediaType mediaType) {
        this.fileName = fileName;
        this.mediaType = mediaType;
    }

    public String getFileName() {
        return fileName;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public static String getMetadataExtension() {
        return METADATA_EXT;
    }
}
