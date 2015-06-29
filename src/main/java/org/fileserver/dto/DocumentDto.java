package org.fileserver.dto;

import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class DocumentDto {

    private final String id;
    private final byte[] bytes;
    private final String fileName;
    private final MediaType mediaType;

    public DocumentDto(byte[] bytes, String fileName, MediaType mediaType) {
        this(null, bytes, fileName, mediaType);
    }

    public DocumentDto(String id, byte[] bytes, String fileName, MediaType mediaType) {
        this.id = id;
        this.bytes = bytes;
        this.fileName = fileName;
        this.mediaType = mediaType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getFileName() {
        return fileName;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getId() {
        return id;
    }

    public long length() {
        return this.bytes.length;
    }

    public InputStream asInputStream() {
        return new ByteArrayInputStream(this.bytes);
    }
}
