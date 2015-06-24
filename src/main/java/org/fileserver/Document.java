package org.fileserver;

public class Document {
    private DocumentMetadata metadata;
    private byte[] bytes;

    public Document(byte[] bytes, String originalFilename) {
        this.bytes = bytes;
        this.metadata = new DocumentMetadata(originalFilename);
    }

    public DocumentMetadata getMetadata() {
        return metadata;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
