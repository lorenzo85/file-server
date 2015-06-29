package org.fileserver.domain;

import org.springframework.http.MediaType;

import javax.persistence.*;

@Entity
@Table(name="metadata")
public class Metadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name = "file_name")
    String fileName;
    @Column(name = "media_type")
    MediaType mediaType;
    @OneToOne
    @JoinColumn(name = "file_id")
    File file;

    public Metadata() {
    }

    public Metadata(File file, String fileName, MediaType mediaType) {
        this.file = file;
        this.fileName = fileName;
        this.mediaType = mediaType;
    }

    public String getFileName() {
        return fileName;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

}
