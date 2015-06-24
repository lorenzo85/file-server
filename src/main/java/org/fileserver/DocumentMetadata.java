package org.fileserver;

import java.io.Serializable;

public class DocumentMetadata implements Serializable {

    public static final String METADATA_EXT = ".metadata";

    String name;

    public DocumentMetadata() {
    }

    public DocumentMetadata(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
