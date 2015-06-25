package org.fileserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

public class Document {

    @JsonIgnore
    private final byte[] bytes;
    private final UUID uuid;
    private final DocumentMetadata metadata;

    public Document(String uuid, DocumentMetadata documentMetadata) {
        this.uuid = UUID.fromString(uuid);
        this.bytes = null;
        this.metadata = documentMetadata;
    }

    public Document(byte[] bytes, String uuid, DocumentMetadata metadata) {
        this.bytes = bytes;
        this.metadata = metadata;
        this.uuid = UUID.fromString(uuid);
    }

    public Document(byte[] bytes, DocumentMetadata metadata) {
        this.uuid = UUID.randomUUID();
        this.bytes = bytes;
        this.metadata = metadata;
    }

    public DocumentMetadata getMetadata() {
        return metadata;
    }

    public byte[] getBytes() {
        if (this.bytes != null) {
            return this.bytes.clone();
        }
        return null;
    }

    public InputStream asInputStream() {
        return new ByteArrayInputStream(this.bytes);
    }

    public int length() {
        return this.bytes.length;
    }

    public String getIdentifier() {
        return uuid.toString();
    }
}
